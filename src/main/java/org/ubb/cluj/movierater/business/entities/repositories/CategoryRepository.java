package org.ubb.cluj.movierater.business.entities.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.entities.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by somai on 22.12.2014.
 */
@Repository
@Transactional(readOnly = true)
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Category save(Category category) {
        entityManager.persist(category);
        return category;
    }

    @Transactional
    public List<Category> getAllCategories() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Transactional
    public Set<Category> getCategoriesById(Long... categoryId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);

        criteriaQuery.select(categoryRoot);

        criteriaQuery.where(categoryRoot.get("id").in((Object[]) categoryId));
        TypedQuery<Category> query = entityManager.createQuery(criteriaQuery);
        return new HashSet<>(query.getResultList());
    }

}
