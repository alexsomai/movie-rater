package org.ubb.cluj.movierater.business.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ubb.cluj.movierater.business.entities.Category;
import org.ubb.cluj.movierater.business.entities.repositories.CategoryRepository;
import org.ubb.cluj.movierater.web.commandobject.CategoryCommandObject;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by somai on 22.12.2014.
 */
@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    CategoryRepository categoryRepository;

    @PostConstruct
    public void initialize() {
        // create categories if there aren't any
        if (categoryRepository.getAllCategories().size() == 0) {
            LOGGER.warn("Creating categories...");
            categoryRepository.save(new Category("Action"));
            categoryRepository.save(new Category("Adventure"));
            categoryRepository.save(new Category("Animation"));
            categoryRepository.save(new Category("Comedy"));
            categoryRepository.save(new Category("Documentary"));
            categoryRepository.save(new Category("Drama"));
            categoryRepository.save(new Category("Family"));
            categoryRepository.save(new Category("Fantasy"));
            categoryRepository.save(new Category("Film-Noir"));
            categoryRepository.save(new Category("History"));
            categoryRepository.save(new Category("Horror"));
            categoryRepository.save(new Category("Music"));
            categoryRepository.save(new Category("Musical"));
            categoryRepository.save(new Category("Mystery"));
            categoryRepository.save(new Category("Romance"));
            categoryRepository.save(new Category("Sci-Fi"));
            categoryRepository.save(new Category("Sport"));
            categoryRepository.save(new Category("Thriller"));
            categoryRepository.save(new Category("War"));
            categoryRepository.save(new Category("Western"));
        }
    }

    public List<CategoryCommandObject> getAllCategories() {
        List<Category> categories = categoryRepository.getAllCategories();
        List<CategoryCommandObject> categoryCommandObjects = new ArrayList<>();
        for (Category category : categories) {
            categoryCommandObjects.add(convertCategoryEntityToCommandObject(category));
        }

        return categoryCommandObjects;
    }

    private CategoryCommandObject convertCategoryEntityToCommandObject(Category category) {
        CategoryCommandObject categoryCommandObject = new CategoryCommandObject();
        categoryCommandObject.setGenre(category.getGenre());
        categoryCommandObject.setId(category.getId());

        return categoryCommandObject;
    }

}