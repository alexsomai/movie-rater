package org.ubb.cluj.movierater.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.ubb.cluj.movierater.business.model.Category;
import org.ubb.cluj.movierater.business.model.Movie;
import org.ubb.cluj.movierater.business.repository.CategoryRepository;
import org.ubb.cluj.movierater.business.repository.MovieRepository;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.ubb.cluj.movierater.business.model.Account.ROLE_ADMIN;
import static org.ubb.cluj.movierater.business.model.Account.ROLE_USER;

/**
 * Created by somai on 11.12.2014.
 */
@Service
public class MovieService {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Secured(ROLE_ADMIN)
    public String save(MovieCommandObject movieCommandObject) {
        return movieRepository.saveOrUpdate(movieCommandObject.createMovie(),
                categoryRepository.getCategoriesById(movieCommandObject.getGenreIds()));
    }

    @Secured({ROLE_USER, ROLE_ADMIN})
    public MovieCommandObject getMovieById(Long id) {
        return convertMovieEntityToCommandObject(movieRepository.getMovieById(id));
    }

    @Secured(ROLE_ADMIN)
    public String update(MovieCommandObject movieCommandObject) {
        Movie movie = movieRepository.getMovieById(movieCommandObject.getId());
        movie.setTitle(movieCommandObject.getTitle());
        movie.setDescription(movieCommandObject.getDescription());
        movie.setReleaseDate(movieCommandObject.getReleaseDate());
        return movieRepository
                .saveOrUpdate(movie, categoryRepository.getCategoriesById(movieCommandObject.getGenreIds()));
    }

    public Long countResults(SearchFilter searchFilter) {
        return movieRepository.countResults(searchFilter);
    }

    public int calculateNumberOfPages(Long numberOfResults) {
        return (int) Math.ceil((double) numberOfResults / MovieRepository.MAX_ITEMS_PER_PAGE);
    }

    public List<MovieCommandObject> findAll(SearchFilter searchFilter) {
        List<MovieCommandObject> movieCommandObjects = new ArrayList<>();
        List<Movie> movieEntities = movieRepository.findAll(searchFilter);
        for (Movie movie : movieEntities) {
            movieCommandObjects.add(convertMovieEntityToCommandObject(movie));
        }
        return movieCommandObjects;
    }

    @Secured(ROLE_ADMIN)
    public String deleteMovie(Long movieId) {
        return movieRepository.deleteMovie(movieRepository.getMovieById(movieId));
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
        Long[] genreIds = new Long[movie.getCategories().size()];
        int arrayIndex = 0;
        for (Category category : movie.getCategories()) {
            genreList.add(category.getGenre());
            genreIds[arrayIndex] = category.getId();
            arrayIndex++;
        }
        movieCommandObject.setGenreNames(genreList);
        movieCommandObject.setGenreIds(genreIds);

        return movieCommandObject;
    }

}
