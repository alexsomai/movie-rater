package org.ubb.cluj.movierater.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ubb.cluj.movierater.business.entities.repositories.CategoryRepository;

/**
 * Created by somai on 22.12.2014.
 */
@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

}
