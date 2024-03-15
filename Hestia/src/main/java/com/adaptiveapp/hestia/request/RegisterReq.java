package com.adaptiveapp.hestia.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegisterReq {

    //When register, those things are mandatory
    @NotBlank(message = "Telephone can't be empty")
    private String telephone;
    @NotBlank(message = "Password can't be empty")
    private String password;
    @NotBlank(message = "Nickname can't be empty")
    private String nickName;
    @NotNull(message = "gender can't be empty")
    private Integer gender;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
