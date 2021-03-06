package org.ubb.cluj.movierater.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ubb.cluj.movierater.business.model.Category;
import org.ubb.cluj.movierater.business.model.Movie;
import org.ubb.cluj.movierater.business.repository.CategoryRepository;
import org.ubb.cluj.movierater.business.repository.MovieRepository;
import org.ubb.cluj.movierater.web.commandobject.MovieCommandObject;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;

import java.util.*;

import static java.io.File.separator;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Alexandru Somai
 *         date 12/29/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService = new MovieService();
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSave() {
        // arrange
        when(movieRepository.saveOrUpdate(any(Movie.class), anySet())).thenReturn("title");

        // act
        String title = movieService.save(mock(MovieCommandObject.class));

        // assert
        assertThat(title, is("title"));
    }

    @Test
    public void shouldGetMovieById() {
        // arrange
        Movie movie = new Movie("title", "desc", "poster", new Date());
        Set<Category> categories = new HashSet<>();
        categories.add(new Category("action"));
        movie.setCategories(categories);
        when(movieRepository.getMovieById(1L)).thenReturn(movie);

        // act
        MovieCommandObject mco = movieService.getMovieById(1L);

        // assert
        String expectedPath = separator + "resources" + separator + "images" + separator + "movie-poster" + separator + "poster";
        assertThat(mco.getTitle(), is("title"));
        assertThat(mco.getDescription(), is("desc"));
        assertThat(mco.getPosterFile(), is(expectedPath));
        assertThat(mco.getGenreIds().length, is(1));
        assertThat(mco.getGenreNames(), contains("action"));
    }

    @Test
    public void shouldUpdateMovie() {
        // arrange
        MovieCommandObject mco = new MovieCommandObject();
        mco.setId(1L);
        Movie movie = new Movie();
        when(movieRepository.saveOrUpdate(any(Movie.class), anySet())).thenReturn("title");
        when(movieRepository.getMovieById(mco.getId())).thenReturn(movie);

        // act
        String title = movieService.update(mco);

        // assert
        assertThat(title, is("title"));
    }

    @Test
    public void shouldCountResults() {
        // arrange
        SearchFilter searchFilter = new SearchFilter();
        when(movieRepository.countResults(searchFilter)).thenReturn(1L);

        // act
        Long results = movieService.countResults(searchFilter);

        // assert
        assertThat(results, is(1L));
    }

    @Test
    public void shouldCalculateNumberOfPages() {
        // act
        int result = movieService.calculateNumberOfPages(10L);

        // assert
        assertThat(result, is(2));
    }

    @Test
    public void shouldFindAllMovies() {
        // arrange
        SearchFilter searchFilter = new SearchFilter();
        Movie movie = new Movie("title", "desc", "poster", new Date());
        when(movieRepository.findAll(searchFilter)).thenReturn(Collections.singletonList(movie));

        // act
        List<MovieCommandObject> mcos = movieService.findAll(searchFilter);

        // assert
        String expectedPath = separator + "resources" + separator + "images" + separator + "movie-poster" + separator + "poster";
        assertThat(mcos.size(), is(1));
        MovieCommandObject mco = mcos.get(0);
        assertThat(mco.getTitle(), is(movie.getTitle()));
        assertThat(mco.getDescription(), is(movie.getDescription()));
        assertThat(mco.getPosterFile(), is(expectedPath));
    }

    @Test
    public void shouldDeleteMovie() {
        // arrange
        when(movieRepository.deleteMovie(any(Movie.class))).thenReturn("title");

        // act
        String title = movieService.deleteMovie(1L);

        // assert
        assertThat(title, is("title"));
    }
}