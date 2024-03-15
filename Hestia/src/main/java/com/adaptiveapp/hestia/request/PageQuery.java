package com.adaptiveapp.hestia.request;

public class PageQuery {


    private Integer page = 1;
    //20 results/page
    private Integer size = 20;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
