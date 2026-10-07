package com.shanzhu.market.entity.query;

public class QueryGoods extends BaseQuery {
    private Long id;
    private String name;
    private Double sellPrice;
    private Long categoryId;
    private String state;
    private String operateStartTime;
    private String operateEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOperateStartTime() {
        return operateStartTime;
    }

    public void setOperateStartTime(String operateStartTime) {
        this.operateStartTime = operateStartTime;
    }

    public String getOperateEndTime() {
        return operateEndTime;
    }

    public void setOperateEndTime(String operateEndTime) {
        this.operateEndTime = operateEndTime;
    }
}
