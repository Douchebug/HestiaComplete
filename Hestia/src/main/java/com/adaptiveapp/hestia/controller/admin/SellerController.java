package com.adaptiveapp.hestia.controller.admin;


import com.adaptiveapp.hestia.common.*;
import com.adaptiveapp.hestia.model.SellerModel;
import com.adaptiveapp.hestia.request.PageQuery;
import com.adaptiveapp.hestia.request.SellerCreateReq;
import com.adaptiveapp.hestia.service.SellerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller("/admin/seller")
@RequestMapping("/admin/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;


    @RequestMapping("/index")
    @AdminPermission
    //list of the sellers
    public ModelAndView index(PageQuery pageQuery){

        PageHelper.startPage(pageQuery.getPage(), pageQuery.getSize());

        List<SellerModel> sellerModelList = sellerService.selectAll();
        PageInfo<SellerModel> sellerModelPageInfo = new PageInfo<>(sellerModelList);

        ModelAndView modelAndView = new ModelAndView("/admin/seller/index.html");
        modelAndView.addObject("data", sellerModelList);
        modelAndView.addObject("CONTROLLER_NAME","seller");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }


    @RequestMapping("/createpage")
    @AdminPermission
    public ModelAndView createPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/seller/create.html");
        modelAndView.addObject("CONTROLLER_NAME","seller");
        modelAndView.addObject("ACTION_NAME","create");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @AdminPermission

    public String create(@Valid SellerCreateReq sellerCreateReq, BindingResult bindingResult) throws BusinessException {
        if(bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }

        SellerModel sellerModel = new SellerModel();
        sellerModel.setName(sellerCreateReq.getName());

        sellerService.create(sellerModel);
        return "redirect:/admin/seller/index";
    }

    @RequestMapping(value = "down", method = RequestMethod.POST)
    @AdminPermission
    @ResponseBody
    public CommonRes down(@RequestParam(value = "id")Integer id) throws BusinessException {
        SellerModel sellerModel = sellerService.changeStatus(id, 1);
        return CommonRes.create(sellerModel);
    }

    @RequestMapping(value = "up", method = RequestMethod.POST)
    @AdminPermission
    @ResponseBody
    public CommonRes up(@RequestParam(value = "id")Integer id) throws BusinessException {
        SellerModel sellerModel = sellerService.changeStatus(id, 0);
        return CommonRes.create(sellerModel);
    }

}
