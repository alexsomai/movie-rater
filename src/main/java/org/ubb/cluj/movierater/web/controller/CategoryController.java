package org.ubb.cluj.movierater.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.ubb.cluj.movierater.business.services.CategoryService;

/**
 * Created by somai on 22.12.2014.
 */
@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

}
