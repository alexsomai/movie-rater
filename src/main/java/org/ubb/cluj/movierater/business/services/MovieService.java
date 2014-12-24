package org.ubb.cluj.movierater.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ubb.cluj.movierater.business.entities.Account;
import org.ubb.cluj.movierater.business.entities.Category;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.MovieAccount;
import org.ubb.cluj.movierater.business.entities.repositories.MovieRepository;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;
import org.ubb.cluj.movierater.web.commandobject.MovieRateResponse;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;

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

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Autowired
    private MovieRepository movieRepository;

    public Movie save(MovieCommandObject movieCommandObject) {
        return movieRepository.save(movieCommandObject.createMovie(), movieCommandObject.getGenreIds());
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
        movieRateResponse.setRatedAt(SIMPLE_DATE_FORMAT.format(movieAccount.getRatedAt()));
        movieRateResponse.setMovieRate(DECIMAL_FORMAT.format(movieAccount.getMovie().getRate()));
        movieRateResponse.setUserRate(DECIMAL_FORMAT.format(movieAccount.getStars()));
        movieRateResponse.setRatings(movieAccount.getMovie().getNumberOfRatings());
        return movieRateResponse;
    }

    public MovieCommandObject getMovieById(Long id) {
        return convertMovieEntityToCommandObject(movieRepository.getMovieById(id));
    }

    public void update(MovieCommandObject movieCommandObject) {
        Movie movie = movieRepository.getMovieById(movieCommandObject.getId());
        movie.setTitle(movieCommandObject.getTitle());
        movie.setDescription(movieCommandObject.getDescription());
        movieRepository.update(movie);
    }

    public int getNumberOfPages(SearchFilter searchFilter) {
        return (int) Math.ceil((double) movieRepository.countResults(searchFilter) / MovieRepository.MAX_ITEMS_PER_PAGE);
    }

    public List<MovieCommandObject> findAll(SearchFilter searchFilter) {
        List<MovieCommandObject> movieCommandObjects = new ArrayList<>();
        List<Movie> movieEntities = movieRepository.findAll(searchFilter);
        for (Movie movie : movieEntities) {
            movieCommandObjects.add(convertMovieEntityToCommandObject(movie));
        }
        return movieCommandObjects;
    }

    private MovieCommandObject convertMovieEntityToCommandObject(Movie movie) {
        MovieCommandObject movieCommandObject = new MovieCommandObject();
        movieCommandObject.setTitle(movie.getTitle());
        movieCommandObject.setDescription(movie.getDescription());
        movieCommandObject.setId(movie.getId());
        movieCommandObject.setPosterFile(PosterService.getRelativeSavingFolder() + movie.getPoster());
        movieCommandObject.setReleaseDate(movie.getReleaseDate());
        movieCommandObject.setNumberOfRatings(movie.getNumberOfRatings());
        movieCommandObject.setRate(DECIMAL_FORMAT.format(movie.getRate().doubleValue()));

        List<String> genreList = new ArrayList<>();
        for (Category category : movie.getCategories()) {
            genreList.add(category.getGenre());
        }
        movieCommandObject.setGenreNames(genreList);

        return movieCommandObject;
    }

}
