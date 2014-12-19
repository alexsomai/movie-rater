package org.ubb.cluj.movierater.business.entities.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.entities.Account;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.MovieAccount;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        account.getMovieAccounts().add(movieAccount);

        entityManager.persist(movie);
        entityManager.persist(movieAccount);
        System.out.println("\"\" = " + "");
    }

    @Transactional
    public MovieAccount getRatingInfo(long movieId, long accountId) {
        // TODO add a named query
        MovieAccount movieAccount = new MovieAccount();
        movieAccount.setStars(1.1);
//        return entityManager.createQuery("SELECT ma FROM MovieAccountId ma WHERE ma.account_id = 2", MovieAccount.class)
//                .setParameter("accountId", accountId)
//                .setParameter("movieId", movieId)
//                .getSingleResult();
        return movieAccount;
    }

    @Transactional
    public Movie getMovieById(Long id) {
        return entityManager.find(Movie.class, id);
    }

    @Transactional
    public void update(Movie movie) {
        // TODO this should be updated!
        entityManager.merge(movie);
    }

    @Transactional
    public List<Movie> findAll() {
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }
}
