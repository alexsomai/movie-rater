package org.ubb.cluj.movierater.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ubb.cluj.movierater.business.entities.Account;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.services.MovieService;
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
        model.addAttribute("id", id);
        return "movie/view";
    }

    @RequestMapping(value = "rate", method = RequestMethod.POST)
    public String rate(long id, String stars) {
        System.out.println("id = " + id);
        System.out.println("stars = " + stars);
        return "movie/view";
    }

}
