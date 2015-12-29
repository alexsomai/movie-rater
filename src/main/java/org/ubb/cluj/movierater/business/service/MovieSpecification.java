package org.ubb.cluj.movierater.business.service;

import org.springframework.data.jpa.domain.Specification;
import org.ubb.cluj.movierater.business.model.Category;
import org.ubb.cluj.movierater.business.model.Movie;

import javax.persistence.criteria.*;

/**
 * @author Alexandru Somai
 *         date 12/29/2015
 */
public class MovieSpecification implements Specification<Movie> {

    private String title;
    private Long[] categories;

    public MovieSpecification(String title, Long[] categories) {
        this.title = title;
        this.categories = categories;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate p = cb.conjunction();
        if (title != null) {
            p = cb.and(p, cb.like(root.<String>get("title"), "%" + title + "%"));
        }
        if (categories.length > 0) {
            Join<Movie, Category> joinCategories = root.join("categories");
            query.distinct(true);
            p = cb.and(p, joinCategories.get("id").in((Object[]) categories));
        }
        query.where(p);
        return p;
    }
}

