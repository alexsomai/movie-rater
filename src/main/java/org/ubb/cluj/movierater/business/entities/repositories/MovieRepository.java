package org.ubb.cluj.movierater.business.entities.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.entities.Account;
import org.ubb.cluj.movierater.business.entities.Category;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.business.entities.MovieAccount;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by somai on 10.12.2014.
 */
@Repository
@Transactional(readOnly = true)
public class MovieRepository {

    private static final int LIMIT_ITEMS_PER_PAGE = 3;
    private static final String SORTING_ASC = "asc";
    private static final String SORTING_DESC = "desc";
    private static final List<String> AVAILABLE_SORTERS = Arrays.asList("rate");

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Movie save(Movie movie, Long[] categoryIds) {
        movie.setCategories(getCategoriesByIds(categoryIds));
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> mr = cq.from(Movie.class);
        Predicate p = cb.conjunction();

        cq.select(mr);

        if (searchFilter.getTitle() != null) {
            p = cb.and(p, cb.like(mr.<String>get("title"), "%" + searchFilter.getTitle() + "%"));
        }

        if (searchFilter.getCategory().length > 0) {
            Join<Movie, Category> categories = mr.join("categories");
            cq.distinct(true);
            p = cb.and(p, categories.get("id").in((Object[]) searchFilter.getCategory()));
        }

        cq.where(p);

        if (AVAILABLE_SORTERS.contains(searchFilter.getSort())) {
            if (searchFilter.getOrder().equals(SORTING_ASC)) {
                cq.orderBy(cb.asc(mr.get(searchFilter.getSort())));
            }
            if (searchFilter.getOrder().equals(SORTING_DESC)) {
                cq.orderBy(cb.desc(mr.get(searchFilter.getSort())));
            }
        }

        return entityManager.createQuery(cq);
    }

    private void addPagination(TypedQuery<Movie> query, int page) {
        query.setMaxResults(LIMIT_ITEMS_PER_PAGE);
        query.setFirstResult(page * LIMIT_ITEMS_PER_PAGE);
    }

    private Set<Category> getCategoriesByIds(Long[] categoryIds) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);

        criteriaQuery.select(categoryRoot);

        criteriaQuery.where(categoryRoot.get("id").in((Object[]) categoryIds));
        TypedQuery<Category> query = entityManager.createQuery(criteriaQuery);
        return new HashSet<>(query.getResultList());
    }

}
