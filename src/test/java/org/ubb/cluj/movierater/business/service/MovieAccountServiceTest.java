package org.ubb.cluj.movierater.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ubb.cluj.movierater.business.model.Account;
import org.ubb.cluj.movierater.business.model.Movie;
import org.ubb.cluj.movierater.business.model.MovieAccount;
import org.ubb.cluj.movierater.business.repository.AccountRepository;
import org.ubb.cluj.movierater.business.repository.MovieAccountRepository;
import org.ubb.cluj.movierater.business.repository.MovieRepository;
import org.ubb.cluj.movierater.web.commandobject.MovieRateResponse;

import javax.persistence.EntityExistsException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Alexandru Somai
 *         date 12/29/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieAccountServiceTest {

    @InjectMocks
    private MovieAccountService movieAccountService = new MovieAccountService();
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private MovieAccountRepository movieAccountRepository;

    @Test
    public void shouldGetRatingInfo() {
        // arrange
        MovieAccount expectedMovieAccount = new MovieAccount();
        Movie movie = new Movie();
        Account account = new Account("user", "demo", "ROLE_USER");
        when(accountRepository.findByUsername("user")).thenReturn(account);
        when(movieRepository.getMovieById(1)).thenReturn(movie);
        when(movieAccountRepository.getRatingInfo(movie, account)).thenReturn(expectedMovieAccount);

        // act
        MovieAccount actualMovieAccount = movieAccountService.getRatingInfo(1, "user");

        // assert
        assertThat(actualMovieAccount, is(expectedMovieAccount));
    }

    @Test
    public void shouldNotRateWhenNotLoggedIn() {
        // act
        MovieRateResponse response = movieAccountService.rate(1, null, 2);

        // assert
        assertThat(response.isSuccess(), is(false));
        assertThat(response.getMessage(), is("You are not logged in!"));
    }

    @Test
    public void shouldNotRateWhenIsAdmin() {
        // arrange
        Principal principal = mock(Principal.class);
        Account account = new Account("admin", "admin", Account.ROLE_ADMIN);
        when(principal.getName()).thenReturn("admin");
        when(accountRepository.findByUsername("admin")).thenReturn(account);

        // act
        MovieRateResponse response = movieAccountService.rate(1, principal, 2);

        // assert
        assertThat(response.isSuccess(), is(false));
        assertThat(response.getMessage(), is("As admin, you are not allowed to rate a movie!"));
    }

    @Test
    public void shouldNotRateTwice() {
        // arrange
        Principal principal = mock(Principal.class);
        Account account = new Account("admin", "admin", Account.ROLE_USER);
        Movie movie = new Movie();
        when(principal.getName()).thenReturn("admin");
        when(accountRepository.findByUsername("admin")).thenReturn(account);
        when(movieRepository.getMovieById(1)).thenReturn(movie);
        when(movieAccountRepository.rate(movie, account, 2)).thenThrow(new EntityExistsException());

        // act
        MovieRateResponse response = movieAccountService.rate(1, principal, 2);

        // assert
        assertThat(response.isSuccess(), is(false));
        assertThat(response.getMessage(), is("You have already rated this movie!"));
    }

    @Test
    public void shouldSuccessfullyRate() {
        // arrange
        Principal principal = mock(Principal.class);
        Account account = new Account("admin", "admin", Account.ROLE_USER);
        Movie movie = new Movie();
        MovieAccount movieAccount = new MovieAccount();
        movieAccount.setStars(BigDecimal.ONE);
        movieAccount.setMovie(movie);
        movieAccount.setRatedAt(new Date());

        when(principal.getName()).thenReturn("admin");
        when(accountRepository.findByUsername("admin")).thenReturn(account);
        when(movieRepository.getMovieById(1)).thenReturn(movie);
        when(movieAccountRepository.rate(movie, account, 2)).thenReturn(movieAccount);

        // act
        MovieRateResponse response = movieAccountService.rate(1, principal, 2);

        // assert
        assertThat(response.isSuccess(), is(true));
        assertThat(response.getMessage(), is("You have rated this movie with 1 stars"));
        assertThat(response.getRatings(), is(movie.getNumberOfRatings()));
    }
}