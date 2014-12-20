package org.ubb.cluj.movierater.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ubb.cluj.movierater.business.entities.Account;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.MovieAccount;
import org.ubb.cluj.movierater.business.entities.repositories.MovieRepository;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;
import org.ubb.cluj.movierater.web.commandobject.MovieRateResponse;

import javax.persistence.EntityExistsException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by somai on 11.12.2014.
 */
@Service
public class MovieService {

    private static final int DESC_MAX_LENGTH = 100;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
    private static final DecimalFormat df = new DecimalFormat("0.00");
    @Autowired
    private MovieRepository movieRepository;

    public Movie save(MovieCommandObject movieCommandObject) {
        return movieRepository.save(movieCommandObject.createMovie());
    }

    public MovieAccount getRatingInfo(long movieId, Account account) {
        return movieRepository
                .getRatingInfo(movieRepository.getMovieById(movieId), account);
    }

    public MovieRateResponse rate(long movieId, long accountId, double stars) {
        MovieRateResponse movieRateResponse = new MovieRateResponse();

        MovieAccount movieAccount;
        try {
            movieAccount = movieRepository.rate(movieId, accountId, stars);
        } catch (EntityExistsException e) {
            movieRateResponse.setSuccess(false);
            return movieRateResponse;
        }
        movieRateResponse.setSuccess(true);
        movieRateResponse.setRatedAt(sdf.format(movieAccount.getRatedAt()));
        movieRateResponse.setMovieRate(df.format(movieAccount.getMovie().getRate()));
        movieRateResponse.setUserRate(df.format(movieAccount.getStars()));
        movieRateResponse.setRatings(movieAccount.getMovie().getNumberOfRatings());
        return movieRateResponse;
    }

    public Movie getMovieById(Long id) {
        return movieRepository.getMovieById(id);
    }

    public void update(MovieCommandObject movieCommandObject) {
        Movie movie = movieRepository.getMovieById(movieCommandObject.getId());
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
