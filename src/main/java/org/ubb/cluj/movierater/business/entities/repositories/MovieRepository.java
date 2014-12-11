package org.ubb.cluj.movierater.business.entities.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.entities.Message;
import org.ubb.cluj.movierater.business.entities.Movie;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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
        entityManager.persist(movie);
        return movie;
    }

    public List<Movie> findAll(){
        return entityManager.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }
}
