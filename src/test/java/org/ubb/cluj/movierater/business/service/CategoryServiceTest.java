package org.ubb.cluj.movierater.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ubb.cluj.movierater.business.model.Category;
import org.ubb.cluj.movierater.business.repository.CategoryRepository;
import org.ubb.cluj.movierater.web.commandobject.CategoryCommandObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Alexandru Somai
 *         date 12/29/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService = new CategoryService();
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void shouldInitializeWithCategories() {
        // act
        categoryService.initialize();

        // assert
        verify(categoryRepository, times(21)).save(any(Category.class));
    }

    @Test
    public void shouldReturnAListOfCategories() {
        // arrange
        List<Category> categories = buildCategories();
        when(categoryRepository.getAllCategories()).thenReturn(categories);

        // act
        List<CategoryCommandObject> categoryCommandObjects = categoryService.getAllCategories();

        // assert
        assertThat(categoryCommandObjects.size(), is(1));
        assertThat(categoryCommandObjects.get(0).getGenre(), is("Action"));
        assertThat(categoryCommandObjects.get(0).getId(), is(nullValue()));
    }

    private static List<Category> buildCategories() {
        Category category = new Category();
        category.setGenre("Action");
        category.setMovies(new HashSet<>());

        return Collections.singletonList(category);
    }
}