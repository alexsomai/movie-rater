package org.ubb.cluj.movierater.business.entities.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.entities.Category;
import org.ubb.cluj.movierater.business.entities.Movie;
import org.ubb.cluj.movierater.web.commandobject.SearchFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by somai on 10.12.2014.
 */
@Repository
@Transactional(readOnly = true)
public class MovieRepository {

    public static final int MAX_ITEMS_PER_PAGE = 3;
    private static final String SORTING_ASC = "asc";
    private static final String SORTING_DESC = "desc";
    private static final List<String> AVAILABLE_SORTERS = Arrays.asList("rate", "numberOfRatings");

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder cb;

    @Transactional
    public String save(Movie movie, Long[] categoryIds) {
        movie.setCategories(getCategoriesByIds(categoryIds));
        entityManager.persist(movie);
        return movie.getTitle();
    }

    @Transactional
    public Movie getMovieById(Long id) {
        return entityManager.find(Movie.class, id);
    }

    @Transactional
    public String update(Movie movie, Long[] categoryIds) {
        movie.setCategories(getCategoriesByIds(categoryIds));
        movie = entityManager.merge(movie);
        entityManager.persist(movie);
        return movie.getTitle();
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Movie> findAll(SearchFilter searchFilter) {
        cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> mr = cq.from(Movie.class);
        cq.select(mr);

        TypedQuery<Movie> query = (TypedQuery<Movie>) applyFilters(cq, mr, searchFilter);
        addPagination(query, searchFilter.getPage());

        return query.getResultList();
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public Long countResults(SearchFilter searchFilter) {
        cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Movie> mr = cq.from(Movie.class);
        cq.select(cb.countDistinct(mr));

        TypedQuery<Long> query = (TypedQuery<Long>) applyFilters(cq, mr, searchFilter);

        return query.getSingleResult();
    }

    @Transactional
    public String deleteMovie(Movie movie) {
        movie = entityManager.merge(movie);
        entityManager.remove(movie);
        return movie.getTitle();
    }

    private TypedQuery<?> applyFilters(CriteriaQuery<?> cq, Root<Movie> mr, SearchFilter searchFilter) {
        Predicate p = cb.conjunction();

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
        query.setMaxResults(MAX_ITEMS_PER_PAGE);
        query.setFirstResult(page * MAX_ITEMS_PER_PAGE);
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
