package com.adaptiveapp.hestia.common;

public class CommonRes {
    //return the result of the request, success or fail
    private  String status;
    //if status = success, it means the program returns json
    //if status = fail, data will use common error codes
    private Object data;


    //define a generic method to create the returned object

    public static CommonRes create(Object result){

        return CommonRes.create(result, "success");
    }

    public static CommonRes create(Object result, String status){
        CommonRes commonRes = new CommonRes();
        commonRes.setStatus(status);
        commonRes.setData(result);
        return commonRes;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
