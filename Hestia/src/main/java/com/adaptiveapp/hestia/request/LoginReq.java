package com.adaptiveapp.hestia.request;

import javax.validation.constraints.NotBlank;

public class LoginReq {
    @NotBlank(message = "Telephone can't be empty")
    private String telephone;
    @NotBlank(message = "Password can't be empty")
    private String password;

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
}
