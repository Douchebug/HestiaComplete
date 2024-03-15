package com.adaptiveapp.hestia.service.imple;

import com.adaptiveapp.hestia.common.BusinessException;
import com.adaptiveapp.hestia.common.EmBusinessError;
import com.adaptiveapp.hestia.dal.UserModelMapper;
import com.adaptiveapp.hestia.model.UserModel;
import com.adaptiveapp.hestia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserModelMapper userModelMapper;


    @Override
    public UserModel getUser(Integer id) {

        return userModelMapper.selectByPrimaryKey(id);
    }

    @Override
    //to be coherent
    @Transactional
    public UserModel register(UserModel registerUser) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //database will get an encoded password string
        registerUser.setPassword(encodeByMd5(registerUser.getPassword()));


        registerUser.setCreatedAt(new Date());
        registerUser.setUpdatedAt(new Date());

        try{
            userModelMapper.insertSelective(registerUser);
        }catch (DuplicateKeyException ex){
            //in case the telephone has been registered
            throw new BusinessException(EmBusinessError.REGISTER_DUP_FAIL);
        }
        return getUser(registerUser.getId());
    }

    @Override
    public UserModel login(String telephone, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException {
        UserModel userModel = userModelMapper.selectByTelephoneAndPassword(telephone, encodeByMd5(password));
        if(userModel == null){
            throw new BusinessException(EmBusinessError.LOGIN_FAIL);
        }

        return userModel;
    }

    @Override
    public Integer countAllUser() {
        return userModelMapper.countAllUser();
    }

    //encode password for security
    private String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //encode method: Md5
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        Base64.Encoder base64Encoder = Base64.getEncoder();
        return base64Encoder.encodeToString(messageDigest.digest(str.getBytes("utf-8")));
    }


}
