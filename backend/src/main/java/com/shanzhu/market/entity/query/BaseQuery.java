package com.shanzhu.market.entity.query;


import java.io.Serializable;
import java.util.Map;

public class BaseQuery implements Serializable {
    private Integer currentPage=1;
    private Integer pageSize=3;
    private Map<String,Object> params;//其他请求参数
    public Integer getStart(){
        return (currentPage-1)*pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
