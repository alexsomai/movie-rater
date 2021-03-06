package org.ubb.cluj.movierater.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.ubb.cluj.movierater.business.model.Account;
import org.ubb.cluj.movierater.business.model.Movie;
import org.ubb.cluj.movierater.business.model.MovieAccount;
import org.ubb.cluj.movierater.business.repository.AccountRepository;
import org.ubb.cluj.movierater.business.repository.MovieAccountRepository;
import org.ubb.cluj.movierater.business.repository.MovieRepository;
import org.ubb.cluj.movierater.web.commandobject.MovieRateResponse;

import javax.persistence.EntityExistsException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import static org.ubb.cluj.movierater.business.model.Account.ROLE_ADMIN;
import static org.ubb.cluj.movierater.business.model.Account.ROLE_USER;

/**
 * @author Alexandru Somai
 *         date 25.12.2014
 */
@Service
public class MovieAccountService {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovieAccountRepository movieAccountRepository;

    @Secured({ROLE_USER, ROLE_ADMIN})
    public MovieAccount getRatingInfo(long movieId, String username) {
        Account account = accountRepository.findByUsername(username);
        Movie movie = movieRepository.getMovieById(movieId);

        return movieAccountRepository.getRatingInfo(movie, account);
    }

    public MovieRateResponse rate(long movieId, Principal principal, double stars) {
        MovieRateResponse movieRateResponse = new MovieRateResponse();

        Account account;
        if (principal != null) {
            account = accountRepository.findByUsername(principal.getName());
            if (account.isAdmin()) {
                movieRateResponse.setSuccess(false);
                movieRateResponse.setMessage("As admin, you are not allowed to rate a movie!");
                return movieRateResponse;
            }
        } else {
            movieRateResponse.setSuccess(false);
            movieRateResponse.setMessage("You are not logged in!");
            return movieRateResponse;
        }

        MovieAccount movieAccount;
        try {
            Movie movie = movieRepository.getMovieById(movieId);
            movieAccount = movieAccountRepository.rate(movie, account, stars);
        } catch (EntityExistsException e) {
            movieRateResponse.setSuccess(false);
            movieRateResponse.setMessage("You have already rated this movie!");
            return movieRateResponse;
        }

        movieRateResponse.setSuccess(true);
        movieRateResponse.setMessage("You have rated this movie with "
                + movieAccount.getStars() + " stars");
        movieRateResponse.setRatedAt(SIMPLE_DATE_FORMAT.format(movieAccount.getRatedAt()));
        movieRateResponse.setMovieRate(DECIMAL_FORMAT.format(movieAccount.getMovie().getRate()));
        movieRateResponse.setUserRate(DECIMAL_FORMAT.format(movieAccount.getStars()));
        movieRateResponse.setRatings(movieAccount.getMovie().getNumberOfRatings());
        return movieRateResponse;
    }
}
