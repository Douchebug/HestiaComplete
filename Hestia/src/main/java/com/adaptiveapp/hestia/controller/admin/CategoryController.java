package com.adaptiveapp.hestia.controller.admin;


import com.adaptiveapp.hestia.common.AdminPermission;
import com.adaptiveapp.hestia.common.BusinessException;
import com.adaptiveapp.hestia.common.CommonUtil;
import com.adaptiveapp.hestia.common.EmBusinessError;
import com.adaptiveapp.hestia.model.CategoryModel;
import com.adaptiveapp.hestia.model.SellerModel;
import com.adaptiveapp.hestia.request.CategoryCreateReq;
import com.adaptiveapp.hestia.request.PageQuery;
import com.adaptiveapp.hestia.request.SellerCreateReq;
import com.adaptiveapp.hestia.service.CategoryService;
import com.adaptiveapp.hestia.service.SellerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

//for backend

@Controller("/admin/category")
@RequestMapping("/admin/category")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @RequestMapping("/index")
    @AdminPermission
    //list of the categories
    public ModelAndView index(PageQuery pageQuery){

        PageHelper.startPage(pageQuery.getPage(), pageQuery.getSize());

        List<CategoryModel> categoryModelList = categoryService.selectAll();
        PageInfo<CategoryModel> categoryModelPageInfo = new PageInfo<>(categoryModelList);

        ModelAndView modelAndView = new ModelAndView("/admin/category/index.html");
        modelAndView.addObject("data", categoryModelPageInfo);
        modelAndView.addObject("CONTROLLER_NAME","category");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }


    @RequestMapping("/createpage")
    @AdminPermission
    public ModelAndView createPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/category/create.html");
        modelAndView.addObject("CONTROLLER_NAME","category");
        modelAndView.addObject("ACTION_NAME","create");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @AdminPermission

    public String create(@Valid CategoryCreateReq categoryCreateReq, BindingResult bindingResult) throws BusinessException {
        if(bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(categoryCreateReq.getName());
        categoryModel.setIconUrl(categoryCreateReq.getIconUrl());
        categoryModel.setSort(categoryCreateReq.getSort());

        categoryService.create(categoryModel);

        return "redirect:/admin/category/index";
    }
}
