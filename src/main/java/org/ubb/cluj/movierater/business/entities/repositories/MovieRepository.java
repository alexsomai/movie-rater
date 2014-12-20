package org.ubb.cluj.movierater.business.entities.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.entities.Account;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.MovieAccount;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * Created by somai on 10.12.2014.
 */
@Repository
@Transactional(readOnly = true)
public class MovieRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Movie save(Movie movie) {
//        Movie movie2 = entityManager.find(Movie.class, (long) 1);
//        movie2.setNumberOfRatings(22);
//        new Movie("test many" + RandomStringUtils.randomAlphanumeric(6), "test many");
//        Account account = entityManager.find(Account.class, (long) 1);
//                new Account("test acc" + RandomStringUtils.randomAlphanumeric(6), "test acc", "ROLE_USER");
//        entityManager.persist(account);

//        MovieAccount movieAccount = new MovieAccount();
//        account.getMovieAccounts().add(movieAccount);
//        movieAccount.setAccount(account);
//        movie2.getMovieAccounts().add(movieAccount);
//        movieAccount.setMovie(movie2);
//        movieAccount.setStars(2.2);

//        movie2.getMovieAccounts().add(movieAccount);

//                movie2.getAccounts().add(account);
//        entityManager.persist(account);
//        entityManager.persist(movie2);
//        entityManager.persist(movieAccount);
        entityManager.persist(movie);
        return movie;
    }

    @Transactional
    public void rate(long movieId, long accountId, double stars) {
        Account account = entityManager.find(Account.class, accountId);

        Movie movie = entityManager.find(Movie.class, movieId);

        if (movie.getNumberOfRatings() == 0) {
            movie.setRate(stars);
        } else {
            movie.setRate((movie.getRate() + stars) / 2);
        }
        movie.setNumberOfRatings(movie.getNumberOfRatings() + 1);

        MovieAccount movieAccount = new MovieAccount();
        movieAccount.setAccount(account);
        movieAccount.setMovie(movie);
        movieAccount.setStars(stars);
        movieAccount.setRatedAt(new Date());
        account.getMovieAccounts().add(movieAccount);

        entityManager.persist(movie);
        entityManager.persist(movieAccount);
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

    @Transactional
    public Movie getMovieById(Long id) {
        return entityManager.find(Movie.class, id);
    }

    @Transactional
    public void update(Movie movie) {
        entityManager.merge(movie);
    }

    @Transactional
    public List<Movie> findAll() {
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }
}
