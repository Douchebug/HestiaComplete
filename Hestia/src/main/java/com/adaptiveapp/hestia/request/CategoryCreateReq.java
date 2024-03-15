package com.adaptiveapp.hestia.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryCreateReq {

    @NotBlank(message = "Name can't be empty.")
    private String name;
    @NotBlank(message = "iconUrl can't be empty.")
    private String iconUrl;
    @NotNull(message = "Sort can't be empty.")
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
