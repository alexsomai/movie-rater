package org.ubb.cluj.movierater.business.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.model.Account;
import org.ubb.cluj.movierater.business.model.Movie;
import org.ubb.cluj.movierater.business.model.MovieAccount;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Alexandru Somai
 *         date 25.12.2014
 */
@Repository
@Transactional(readOnly = true)
public class MovieAccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public MovieAccount rate(Movie movie, Account account, double stars) throws EntityExistsException {
        account = entityManager.merge(account);
        movie = entityManager.merge(movie);

        if (movie.getNumberOfRatings() == 0) {
            movie.setRate(BigDecimal.valueOf(stars));
        } else {
            movie.setRate(BigDecimal.valueOf((movie.getRate().doubleValue() + stars) / 2));
        }
        movie.setNumberOfRatings(movie.getNumberOfRatings() + 1);

        MovieAccount movieAccount = new MovieAccount();
        movieAccount.setAccount(account);
        movieAccount.setMovie(movie);
        movieAccount.setStars(BigDecimal.valueOf(stars));
        movieAccount.setRatedAt(new Date());
        movie.getMovieAccounts().add(movieAccount);

        entityManager.persist(movie);
        entityManager.persist(movieAccount);
        return movieAccount;
    }

    @Transactional
    public MovieAccount getRatingInfo(Movie movie, Account account) {
        TypedQuery<MovieAccount> queryResult = entityManager
                .createNamedQuery(MovieAccount.GET_RATING_INFO, MovieAccount.class)
                .setParameter("account", account)
                .setParameter("movie", movie);
        MovieAccount movieAccount = null;
        if (queryResult.getResultList().size() > 0) {
            movieAccount = queryResult.getSingleResult();
        }

        return movieAccount;
    }

}
