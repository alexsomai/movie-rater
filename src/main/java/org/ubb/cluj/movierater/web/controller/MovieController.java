package org.ubb.cluj.movierater.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.MovieAccount;
import org.ubb.cluj.movierater.business.services.MovieService;
import org.ubb.cluj.movierater.business.services.PosterService;
import org.ubb.cluj.movierater.business.services.UserService;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;
import org.ubb.cluj.movierater.web.commandobject.MovieRateResponse;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
        int numberOfPages = movieService.getNumberOfPages(searchFilter.getTitle());
        searchFilter.setNoPages(numberOfPages);
        if (searchFilter.getPage() < 0) {
            searchFilter.setPage(0);
        }
        if (searchFilter.getPage() > numberOfPages - 1) {
            searchFilter.setPage(numberOfPages - 1);
        }
        model.addAttribute("movies", movieService.findAll(searchFilter));
        return "movie/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(MovieCommandObject movieCommandObject) {
        return "movie/add";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute MovieCommandObject movieCommandObject, Errors errors,
                       Model model, @RequestParam("poster") MultipartFile poster) {
        String posterErrorMessage = posterService.validatePoster(poster);
        if (errors.hasErrors() || posterErrorMessage != null) {
            model.addAttribute("posterErrMsg", posterErrorMessage);
            return "movie/add";
        }

        String posterFileName = posterService.savePoster(poster);
        movieCommandObject.setPosterFile(posterFileName);
        movieService.save(movieCommandObject);
        return "admin/admin_page";
    }

    @RequestMapping(value = "/singleSave", method = RequestMethod.POST)
    public
    @ResponseBody
    String singleSave(@RequestParam("file") MultipartFile file, @RequestParam("desc") String desc) {
        System.out.println("File Description:" + desc);
        String fileName = null;
        if (!file.isEmpty()) {
            try {
                fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                File rootFile = new File("/home/somai");
                System.out.println("rootFile = " + rootFile.getAbsolutePath());
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File(rootFile.getAbsolutePath(), fileName)));
                buffStream.write(bytes);
                buffStream.close();
                return "You have successfully uploaded " + fileName;
            } catch (Exception e) {
                return "You failed to upload " + fileName + ": " + e.getMessage();
            }
        } else {
            return "Unable to upload. File is empty.";
        }
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(Model model, Long id) {
        UserService.User user = (UserService.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Movie movie = movieService.getMovieById(id);
        MovieAccount movieAccount = movieService.getRatingInfo(id, user.getAccount());
        model.addAttribute("movie", movie);
        model.addAttribute("movieAccount", movieAccount);
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
        Movie movie = movieService.getMovieById(movieId);
        MovieCommandObject movieCommandObject = new MovieCommandObject();
        movieCommandObject.setDescription(movie.getDescription());
        movieCommandObject.setTitle(movie.getTitle());
        movieCommandObject.setId(movie.getId());
        model.addAttribute("movieCommandObject", movieCommandObject);
        return "movie/edit";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute MovieCommandObject movieCommandObject, Errors errors) {
        if (errors.hasErrors()) {
            return "movie/edit";
        }
        movieService.update(movieCommandObject);
        return "admin/admin_page";
    }

}
