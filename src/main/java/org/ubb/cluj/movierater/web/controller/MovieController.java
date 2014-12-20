package org.ubb.cluj.movierater.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.MovieAccount;
import org.ubb.cluj.movierater.business.services.MovieService;
import org.ubb.cluj.movierater.business.services.UserService;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;

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

    @PostConstruct
    protected void initialize() {
//        movieService.save(new Movie("title", "desc"));
    }

    @ModelAttribute("page")
    public String module() {
        return "movies";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "movie/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(MovieCommandObject movieCommandObject) {
        return "movie/add";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute MovieCommandObject movieCommandObject, Errors errors) {
        if (errors.hasErrors()) {
            return "movie/add";
        }
        movieService.save(movieCommandObject);
        return "admin/admin_page";
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

    @RequestMapping(value = "rate", method = RequestMethod.POST)
    @ResponseBody
    public String rate(Model model, long movieId, double stars) {
        UserService.User user = (UserService.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        movieService.rate(movieId, user.getAccount().getId(), stars);

        return String.valueOf(movieId);
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
