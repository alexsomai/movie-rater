package org.ubb.cluj.movierater.business.entities.repositories;

import org.apache.commons.lang.RandomStringUtils;
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
        Movie movie2 = new Movie("test many" + RandomStringUtils.randomAlphanumeric(6), "test many");
        Account account = new Account("test acc" + RandomStringUtils.randomAlphanumeric(6), "test acc", "ROLE_USER");
        entityManager.persist(account);

        MovieAccount movieAccount = new MovieAccount();
//        account.getMovieAccounts().add(movieAccount);
        movieAccount.setAccount(account);
//        movie2.getMovieAccounts().add(movieAccount);
        movieAccount.setMovie(movie2);
        movieAccount.setStars(2.2);

        movie2.getMovieAccounts().add(movieAccount);

//                movie2.getAccounts().add(account);
//        entityManager.persist(account);
        entityManager.persist(movie2);
        entityManager.persist(movieAccount);

//        entityManager.persist(movie);
        return movie;
    }

    public List<Movie> findAll() {
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }
}
