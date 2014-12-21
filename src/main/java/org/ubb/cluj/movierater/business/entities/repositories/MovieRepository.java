package org.ubb.cluj.movierater.business.entities.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.entities.Account;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.MovieAccount;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by somai on 10.12.2014.
 */
@Repository
@Transactional(readOnly = true)
public class MovieRepository {

    private static final int LIMIT_ITEMS_PER_PAGE = 3;
    private static final String SORTING_ASC = "asc";
    private static final String SORTING_DESC = "desc";
    private static final List<String> AVAILABLE_SORTERS = new ArrayList<String>() {{
        add("rate");
    }};

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Movie save(Movie movie) {
        entityManager.persist(movie);
        return movie;
    }

    @Transactional
    public MovieAccount rate(long movieId, long accountId, double stars) throws EntityExistsException {
        Account account = entityManager.find(Account.class, accountId);

        Movie movie = entityManager.find(Movie.class, movieId);

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
        account.getMovieAccounts().add(movieAccount);

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

    @Transactional
    public Movie getMovieById(Long id) {
        return entityManager.find(Movie.class, id);
    }

    @Transactional
    public void update(Movie movie) {
        entityManager.merge(movie);
    }

    @Transactional
    public List<Movie> findAll(SearchFilter searchFilter) {
        TypedQuery<Movie> query = createQuery(searchFilter);

        addPagination(query, searchFilter.getPage());

        return query.getResultList();
    }

    @Transactional
    public int getNumberOfPages(SearchFilter searchFilter) {
        return (int) Math.ceil((double) createQuery(searchFilter).getResultList().size() / LIMIT_ITEMS_PER_PAGE);
    }

    private TypedQuery<Movie> createQuery(SearchFilter searchFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> movieRoot = criteriaQuery.from(Movie.class);

        criteriaQuery.select(movieRoot);
        if (searchFilter.getSort() != null && searchFilter.getOrder() != null
                && AVAILABLE_SORTERS.contains(searchFilter.getSort())) {
            if (searchFilter.getOrder().equals(SORTING_ASC)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(movieRoot.get(searchFilter.getSort())));
            }
            if (searchFilter.getOrder().equals(SORTING_DESC)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(movieRoot.get(searchFilter.getSort())));
            }
        }

        if (searchFilter.getTitle() != null) {
            criteriaQuery.where(criteriaBuilder.like(movieRoot.<String>get("title"), "%" + searchFilter.getTitle() + "%"));
        }

        return entityManager.createQuery(criteriaQuery);
    }

    private void addPagination(TypedQuery<Movie> query, int page) {
        query.setMaxResults(LIMIT_ITEMS_PER_PAGE);
        query.setFirstResult(page * LIMIT_ITEMS_PER_PAGE);
    }

}
