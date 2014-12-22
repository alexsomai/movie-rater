package org.ubb.cluj.movierater.business.entities.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ubb.cluj.movierater.business.entities.Category;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by somai on 22.12.2014.
 */
@Repository
@Transactional(readOnly = true)
public class CategoryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void createCategories() {
        // create categories if there aren't any
        if (entityManager.createQuery("SELECT c FROM Category").getResultList().size() == 0) {
            LOGGER.info("Creating categories...");
            save(new Category("Action"));
            save(new Category("Adventure"));
            save(new Category("Animation"));
            save(new Category("Comedy"));
            save(new Category("Documentary"));
            save(new Category("Drama"));
            save(new Category("Family"));
            save(new Category("Fantasy"));
            save(new Category("Film-Noir"));
            save(new Category("History"));
            save(new Category("Horror"));
            save(new Category("Music"));
            save(new Category("Musical"));
            save(new Category("Mystery"));
            save(new Category("Romance"));
            save(new Category("Sci-Fi"));
            save(new Category("Sport"));
            save(new Category("Thriller"));
            save(new Category("War"));
            save(new Category("Western"));
        }
    }

    @Transactional
    public Category save(Category category) {
        entityManager.persist(category);
        return category;
    }

}
