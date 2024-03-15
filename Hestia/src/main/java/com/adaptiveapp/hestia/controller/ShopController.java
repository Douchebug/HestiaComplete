package com.adaptiveapp.hestia.controller;

import com.adaptiveapp.hestia.common.BusinessException;
import com.adaptiveapp.hestia.common.CommonRes;
import com.adaptiveapp.hestia.common.EmBusinessError;
import com.adaptiveapp.hestia.model.CategoryModel;
import com.adaptiveapp.hestia.model.ShopModel;
import com.adaptiveapp.hestia.service.CategoryService;
import com.adaptiveapp.hestia.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller("/shop")
@RequestMapping("/shop")

public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryService categoryService;


    //recommendation service

    @RequestMapping("/recommend")
    @ResponseBody
    public CommonRes recommend(@RequestParam(name = "longitude")BigDecimal longitude,
                               @RequestParam(name = "latitude")BigDecimal latitude) throws BusinessException {
        if(longitude == null || latitude == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        List<ShopModel> shopModelList = shopService.recommend(longitude, latitude);
        return CommonRes.create(shopModelList);
    }

    //Search service
    @RequestMapping("/search")
    @ResponseBody
    public CommonRes search(@RequestParam(name = "longitude")BigDecimal longitude,
                            @RequestParam(name = "latitude")BigDecimal latitude,
                            @RequestParam(name = "keyword")String keyword,
                            @RequestParam(name = "orderby", required = false)Integer orderby,
                            @RequestParam(name = "categoryId", required = false)Integer categoryId,
                            @RequestParam(name = "tags", required = false)String tags) throws BusinessException, IOException {
        if(StringUtils.isEmpty(keyword)|| longitude == null || latitude == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }



        Map<String, Object> result = shopService.searchES(longitude, latitude, keyword,orderby,categoryId,tags);
        List<ShopModel> shopModelList = (List<ShopModel>) result.get("shop");
        List<CategoryModel> categoryModelList = categoryService.selectAll();
        //List<Map<String, Object>> tagsAggregation = shopService.searchGroupByTags(keyword, categoryId, tags);
        List<Map<String, Object>> tagsAggregation = (List<Map<String, Object>>) result.get("tags");
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("shop", shopModelList);
        resMap.put("category", categoryModelList);
        resMap.put("tags", tagsAggregation);
        return CommonRes.create(resMap);
    }
}
