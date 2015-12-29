package org.ubb.cluj.movierater.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.ubb.cluj.movierater.business.model.Account.ROLE_ADMIN;
import static org.ubb.cluj.movierater.business.model.Account.ROLE_USER;

/**
 * @author Alexandru Somai
 *         date 11.12.2014
 */
@Service
public class MovieService {

    private static final int MAX_ITEMS_PER_PAGE = 9;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private static final String SORTING_ASC = "asc";
    private static final String SORTING_DESC = "desc";
    private static final List<String> AVAILABLE_SORTERS = Arrays.asList("rate", "numberOfRatings");

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Secured(ROLE_ADMIN)
    @Transactional
    public String save(MovieCommandObject movieCommandObject) {
        List<Category> categories = categoryRepository.findAll(movieCommandObject.getGenreIds());
        Movie movie = movieCommandObject.createMovie();
        movie.setCategories(new HashSet<>(categories));

        return movieRepository.save(movie).getTitle();
    }

    @Secured({ROLE_USER, ROLE_ADMIN})
    public MovieCommandObject getMovieById(Long id) {
        Movie movie = movieRepository.findOne(id);
        return convertEntityToCommandObject(movie);
    }

    @Secured(ROLE_ADMIN)
    @Transactional
    public String update(MovieCommandObject movieCommandObject) {
        List<Category> categories = categoryRepository.findAll(movieCommandObject.getGenreIds());
        Movie movie = movieRepository.findOne(movieCommandObject.getId());
        movie.setTitle(movieCommandObject.getTitle());
        movie.setDescription(movieCommandObject.getDescription());
        movie.setReleaseDate(movieCommandObject.getReleaseDate());
        movie.setCategories(new HashSet<>(categories));

        return movieRepository.save(movie).getTitle();
    }

    @SuppressWarnings("unchecked")
    public Page<MovieCommandObject> findAll(SearchFilter searchFilter) {
        Sort sort = null;
        if (AVAILABLE_SORTERS.contains(searchFilter.getSort())) {
            Sort.Direction direction = Sort.DEFAULT_DIRECTION;
            if (SORTING_ASC.equals(searchFilter.getOrder())) {
                direction = Sort.Direction.ASC;
            } else if (SORTING_DESC.equals(searchFilter.getOrder())) {
                direction = Sort.Direction.DESC;
            }
            sort = new Sort(direction, searchFilter.getSort());
        }

        Pageable pageable = new PageRequest(searchFilter.getPage(), MAX_ITEMS_PER_PAGE, sort);
        Specification specification = new MovieSpecification(searchFilter.getTitle(), searchFilter.getCategory());
        Page<Movie> moviePage = movieRepository.findAll(specification, pageable);

        List<MovieCommandObject> movieCommandObjects = moviePage.getContent().stream()
                .map(MovieService::convertEntityToCommandObject)
                .collect(Collectors.toList());

        return new PageImpl<>(movieCommandObjects, pageable, moviePage.getTotalElements());
    }

    @Secured(ROLE_ADMIN)
    public void deleteMovie(Long movieId) {
        movieRepository.delete(movieId);
    }

    private static MovieCommandObject convertEntityToCommandObject(Movie movie) {
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
