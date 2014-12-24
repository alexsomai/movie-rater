package org.ubb.cluj.movierater.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ubb.cluj.movierater.business.services.MovieService;
import org.ubb.cluj.movierater.business.services.PosterService;
import org.ubb.cluj.movierater.business.services.UserService;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;
import org.ubb.cluj.movierater.web.commandobject.MovieRateResponse;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;
import org.ubb.cluj.movierater.web.support.MessageHelper;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

/**
 * Created by somai on 10.12.2014.
 */
@Controller
@RequestMapping(value = "movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private PosterService posterService;

    @PostConstruct
    protected void initialize() {
//        movieService.save(new Movie("title", "desc"));
    }

    @ModelAttribute("page")
    public String module() {
        return "movies";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(SearchFilter searchFilter, Model model) {
        Long numberOfResults = movieService.countResults(searchFilter);
        searchFilter.setNoPages(movieService.calculateNumberOfPages(numberOfResults));
        model.addAttribute("results", numberOfResults);
        model.addAttribute("movies", movieService.findAll(searchFilter));
        return "movie/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(MovieCommandObject movieCommandObject) {
        return "movie/add";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute MovieCommandObject movieCommandObject, Errors errors,
                       Model model, @RequestParam("poster") MultipartFile poster,
                       RedirectAttributes ra) {
        String posterErrorMessage = posterService.validatePoster(poster);
        if (errors.hasErrors() || posterErrorMessage != null) {
            model.addAttribute("posterErrMsg", posterErrorMessage);
            return "movie/add";
        }

        String posterFileName = posterService.savePoster(poster);
        movieCommandObject.setPosterFile(posterFileName);
        String movieTitle = movieService.save(movieCommandObject);
        MessageHelper.addSuccessAttribute(ra, "message.movie.success.save", movieTitle);
        return "redirect:/movie/index";
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(Model model, Long id) {
        UserService.User user = (UserService.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("movie", movieService.getMovieById(id));
        model.addAttribute("movieAccount", movieService.getRatingInfo(id, user.getAccount()));
        return "movie/view";
    }

    @RequestMapping(value = "rate", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public MovieRateResponse rate(long movieId, double stars) {
        UserService.User user = (UserService.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return movieService.rate(movieId, user.getAccount().getId(), stars);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Long movieId) {
        model.addAttribute("movieCommandObject", movieService.getMovieById(movieId));
        return "movie/edit";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute MovieCommandObject movieCommandObject, Errors errors,
                         RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return "movie/edit";
        }
        String movieTitle = movieService.update(movieCommandObject);
        MessageHelper.addSuccessAttribute(ra, "message.movie.success.update", movieTitle);
        return "redirect:/movie/index";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam("movieId") Long movieId, @ModelAttribute SearchFilter searchFilter,
                         RedirectAttributes ra) {
        ra.addAttribute("page", searchFilter.getPage());
        ra.addAttribute("category", searchFilter.getCategory());
        ra.addAttribute("title", searchFilter.getTitle());
        ra.addAttribute("sort", searchFilter.getSort());
        ra.addAttribute("order", searchFilter.getOrder());
        String movieTitle = movieService.deleteMovie(movieId);
        MessageHelper.addInfoAttribute(ra, "message.movie.success.delete", movieTitle);
        return "redirect:/movie/index";
    }

}
