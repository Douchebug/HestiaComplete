package com.adaptiveapp.hestia.service;

import com.adaptiveapp.hestia.common.BusinessException;
import com.adaptiveapp.hestia.model.CategoryModel;

import java.util.List;

public interface CategoryService {


    CategoryModel create(CategoryModel categoryModel) throws BusinessException;
    CategoryModel get(Integer id);
    List<CategoryModel> selectAll();

    Integer countAllCategory();
}
