package org.ubb.cluj.movierater.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ubb.cluj.movierater.business.service.MovieAccountService;
import org.ubb.cluj.movierater.business.service.MovieService;
import org.ubb.cluj.movierater.business.service.PosterService;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;
import org.ubb.cluj.movierater.web.commandobject.MovieRateResponse;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;
import org.ubb.cluj.movierater.web.support.MessageHelper;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by somai on 10.12.2014.
 */
@Controller
@RequestMapping(value = "movie")
public class MovieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    private static final String ADD_PAGE = "movie/add";
    private static final String EDIT_PAGE = "movie/edit";
    private static final String VIEW_PAGE = "movie/view";
    private static final String INDEX_PAGE = "movie/index";
    private static final String REDIRECT_INDEX = "redirect:/movie/index";

    @Autowired
    private MovieService movieService;

    @Autowired
    private PosterService posterService;

    @Autowired
    private MovieAccountService movieAccountService;

    @ModelAttribute("activePage")
    public String module() {
        return "movies";
    }

    @PreAuthorize("USER_ADMIN")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(@ModelAttribute SearchFilter searchFilter, Model model) {
        Long numberOfResults = movieService.countResults(searchFilter);
        searchFilter.setNoPages(movieService.calculateNumberOfPages(numberOfResults));
        model.addAttribute("results", numberOfResults);
        model.addAttribute("movies", movieService.findAll(searchFilter));
        return INDEX_PAGE;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(@ModelAttribute MovieCommandObject movieCommandObject) {
        return ADD_PAGE;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute MovieCommandObject movieCommandObject, Errors errors,
                       Model model, @RequestParam("poster") MultipartFile poster,
                       RedirectAttributes ra) {
        String posterErrorMessage = posterService.validatePoster(poster);
        if (errors.hasErrors() || posterErrorMessage != null) {
            model.addAttribute("posterErrMsg", posterErrorMessage);
            return ADD_PAGE;
        }

        String posterFileName = posterService.savePoster(poster);
        movieCommandObject.setPosterFile(posterFileName);
        String movieTitle;
        try {
            movieTitle = movieService.save(movieCommandObject);
        } catch (PersistenceException e) {
            if (e.getCause().getCause().getLocalizedMessage().matches(MessageHelper.DUPLICATE_ENTRY_MESSAGE)) {
                LOGGER.warn("Error while saving movie, title must be unique", e);
                errors.rejectValue("title", "message.movie.title.unique", "Movie title must be unique");
                return ADD_PAGE;
            }
            LOGGER.error("An unexpected error occurred", e);
            errors.rejectValue("title", "message.error.unexpected", "An unexpected error occurred!");
            return ADD_PAGE;
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred", e);
            errors.rejectValue("title", "message.error.unexpected", "An unexpected error occurred!");
            return ADD_PAGE;
        }
        MessageHelper.addSuccessAttribute(ra, "message.movie.success.save", movieTitle);
        return REDIRECT_INDEX;
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(Model model, Long id, @ModelAttribute SearchFilter searchFilter, Principal principal) {
        model.addAttribute("movie", movieService.getMovieById(id));
        model.addAttribute("movieAccount", movieAccountService.getRatingInfo(id, principal.getName()));
        return VIEW_PAGE;
    }

    @RequestMapping(value = "rate", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public MovieRateResponse rate(long movieId, double stars, Principal principal) {
        return movieAccountService.rate(movieId, principal, stars);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Long movieId) {
        model.addAttribute("movieCommandObject", movieService.getMovieById(movieId));
        return EDIT_PAGE;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute MovieCommandObject movieCommandObject, Errors errors,
                         RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return EDIT_PAGE;
        }

        String movieTitle;
        try {
            movieTitle = movieService.update(movieCommandObject);
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getMessage().matches(MessageHelper.DUPLICATE_ENTRY_MESSAGE)) {
                LOGGER.warn("Error while updating movie, title must be unique", e);
                errors.rejectValue("title", "message.movie.title.unique", "Movie title must be unique");
                return EDIT_PAGE;
            }
            LOGGER.error("An unexpected error occurred", e);
            errors.rejectValue("title", "message.error.unexpected", "An unexpected error occurred!");
            return EDIT_PAGE;
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred", e);
            errors.rejectValue("title", "message.error.unexpected", "An unexpected error occurred!");
            return EDIT_PAGE;
        }
        MessageHelper.addSuccessAttribute(ra, "message.movie.success.update", movieTitle);
        return REDIRECT_INDEX;
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
        return REDIRECT_INDEX;
    }

}
