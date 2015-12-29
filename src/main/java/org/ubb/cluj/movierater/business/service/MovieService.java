package org.ubb.cluj.movierater.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.model.Category;
import org.ubb.cluj.movierater.business.model.Movie;
import org.ubb.cluj.movierater.business.repository.CategoryRepository;
import org.ubb.cluj.movierater.business.repository.MovieRepository;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public String save(MovieCommandObject movieCommandObject) {
        Movie movie = movieCommandObject.createMovie();
        List<Category> categories = categoryRepository.findAll(movieCommandObject.getGenreIds());
        movie.setCategories(new HashSet<>(categories));

        return movieRepository.saveOrUpdate(movie);
    }

    @Secured({ROLE_USER, ROLE_ADMIN})
    public MovieCommandObject getMovieById(Long id) {
        Movie movie = movieRepository.getMovieById(id);
        return convertMovieEntityToCommandObject(movie);
    }

    @Secured(ROLE_ADMIN)
    @Transactional
    public String update(MovieCommandObject movieCommandObject) {
        List<Category> categories = categoryRepository.findAll(movieCommandObject.getGenreIds());
        Movie movie = movieRepository.getMovieById(movieCommandObject.getId());
        movie.setTitle(movieCommandObject.getTitle());
        movie.setDescription(movieCommandObject.getDescription());
        movie.setReleaseDate(movieCommandObject.getReleaseDate());
        movie.setCategories(new HashSet<>(categories));

        return movieRepository.saveOrUpdate(movie);
    }

    public Long countResults(SearchFilter searchFilter) {
        return movieRepository.countResults(searchFilter);
    }

    public int calculateNumberOfPages(Long numberOfResults) {
        return (int) Math.ceil((double) numberOfResults / MovieRepository.MAX_ITEMS_PER_PAGE);
    }

    public List<MovieCommandObject> findAll(SearchFilter searchFilter) {
        List<Movie> movieEntities = movieRepository.findAll(searchFilter);
        return movieEntities.stream()
                .map(MovieService::convertMovieEntityToCommandObject)
                .collect(Collectors.toList());
    }

    @Secured(ROLE_ADMIN)
    public String deleteMovie(Long movieId) {
        Movie movie = movieRepository.getMovieById(movieId);
        return movieRepository.deleteMovie(movie);
    }

    private static MovieCommandObject convertMovieEntityToCommandObject(Movie movie) {
        MovieCommandObject movieCommandObject = new MovieCommandObject();
        movieCommandObject.setTitle(movie.getTitle());
        movieCommandObject.setDescription(movie.getDescription());
        movieCommandObject.setId(movie.getId());
        movieCommandObject.setPosterFile(PosterService.getRelativeSavingFolder() + movie.getPoster());
        movieCommandObject.setReleaseDate(movie.getReleaseDate());
        movieCommandObject.setNumberOfRatings(movie.getNumberOfRatings());
        movieCommandObject.setRate(DECIMAL_FORMAT.format(movie.getRate().doubleValue()));

        List<String> genreNames = new ArrayList<>(movie.getCategories().size());
        List<Long> genreIds = new ArrayList<>(movie.getCategories().size());
        movie.getCategories().forEach(category -> {
            genreNames.add(category.getGenre());
            genreIds.add(category.getId());
        });
        movieCommandObject.setGenreNames(genreNames);
        movieCommandObject.setGenreIds(genreIds);

        return movieCommandObject;
    }
}
