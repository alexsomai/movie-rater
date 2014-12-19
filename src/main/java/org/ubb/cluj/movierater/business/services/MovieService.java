package org.ubb.cluj.movierater.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ubb.cluj.movierater.business.entities.Account;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.MovieAccount;
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

    public MovieAccount getRatingInfo(long movieId, long accountId){
        return movieRepository.getRatingInfo(movieId,accountId);
    }

    public void rate(long movieId, long accountId, double stars) {
//        Movie movie = movieRepository.getMovieById(movieId);
//        if (movie.getNumberOfRatings() == null) {
//            movie.setNumberOfRatings(1);
//        } else {
//            movie.setNumberOfRatings(movie.getNumberOfRatings() + 1);
//        }
//        if (movie.getRate() == null) {
//            movie.setRate((double) 0);
//        } else {
//            movie.setRate((movie.getRate() + stars) / 2);
//        }
        movieRepository.rate(movieId, accountId, stars);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.getMovieById(id);
    }

    public void update(MovieCommandObject movieCommandObject){
        Movie movie = movieRepository.getMovieById(movieCommandObject.getId());
//        movie.setId(movieCommandObject.getId());
        movie.setTitle(movieCommandObject.getTitle());
        movie.setDescription(movieCommandObject.getDescription());
        movieRepository.update(movie);
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
