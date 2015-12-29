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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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
}