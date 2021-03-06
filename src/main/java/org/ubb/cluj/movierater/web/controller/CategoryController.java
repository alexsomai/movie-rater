package org.ubb.cluj.movierater.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ubb.cluj.movierater.business.service.CategoryService;
import org.ubb.cluj.movierater.web.commandobject.CategoryCommandObject;

import java.util.List;

/**
 * @author Alexandru Somai
 *         date 22.12.2014
 */
@Controller
@RequestMapping(value = "category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<CategoryCommandObject> getCategories() {
        return categoryService.getAllCategories();
    }
}
