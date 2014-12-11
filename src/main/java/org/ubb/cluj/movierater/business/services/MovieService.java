package org.ubb.cluj.movierater.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.repositories.MovieRepository;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by somai on 11.12.2014.
 */
@Service
public class MovieService {

    private static final int DESC_MAX_LENGTH = 100;

    @Autowired
    private MovieRepository movieRepository;

    public Movie save(MovieCommandObject movieCommandObject) {
        return movieRepository.save(movieCommandObject.createMovie());
    }

    public List<MovieCommandObject> findAll() {
        List<MovieCommandObject> movieCommandObjects = new ArrayList<>();
        List<Movie> movieEntities = movieRepository.findAll();
        for (Movie movie : movieEntities) {
            movieCommandObjects.add(convertMovieEntityToCommandObject(movie));
        }
        return movieCommandObjects;
    }

    private MovieCommandObject convertMovieEntityToCommandObject(Movie movie) {
        MovieCommandObject movieCommandObject = new MovieCommandObject();
        movieCommandObject.setTitle(movie.getTitle());
        String description = movie.getDescription();
        if (description.length() > DESC_MAX_LENGTH) {
            description = description.substring(0, DESC_MAX_LENGTH) + " ...";
        }
        movieCommandObject.setDescription(description);
        movieCommandObject.setImage(movie.getImage());
        movieCommandObject.setId(movie.getId());
        return movieCommandObject;
    }
}
