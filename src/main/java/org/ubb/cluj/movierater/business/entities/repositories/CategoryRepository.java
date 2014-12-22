package org.ubb.cluj.movierater.business.entities.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.entities.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public Category getCategoryById(Long id) {
        return entityManager.find(Category.class, id);
    }

}
