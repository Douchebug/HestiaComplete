package com.adaptiveapp.hestia.common;

public enum EmBusinessError {

    //Common error start with 10000
    NO_OBJECT_FOUND(10001, "The requested object doesn't exist"),
    UNKNOWN_ERROR(10002, "Unknown error"),
    NO_HANDLER_FOUND_ERROR(10003, "Can't find this path"),
    BIND_EXCEPTION_ERROR(10004, "Request parameter error"),
    PARAMETER_VALIDATION_ERROR(10005, "Request parameter validation failed"),


    //user service error start with 20000
    REGISTER_DUP_FAIL(20001, "User already exists"),
    LOGIN_FAIL(20002, "Telephone or password wrong"),

    //admin related error start with 30000
    ADMIN_SHOULD_LOGIN(30001, "Admin needs to log in first"),

    //category related error start with 40000
    CATEGORY_NAME_DUPLICATED(40001, "Category name already existed"),;
    private Integer errCode;
    private String errMsg;

    EmBusinessError(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
