package com.adaptiveapp.hestia.service;

import com.adaptiveapp.hestia.common.BusinessException;
import com.adaptiveapp.hestia.model.SellerModel;

import java.util.List;

public interface SellerService {

    SellerModel create(SellerModel sellerModel);

    SellerModel get(Integer id);

    List<SellerModel> selectAll();

    SellerModel changeStatus(Integer id, Integer disabledFlag) throws BusinessException;

    Integer countAllSeller();
}
