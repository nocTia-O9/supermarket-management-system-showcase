package com.shanzhu.market.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class GoodsListVo implements Serializable {

    /*商品编码*/
    private Long id;
    /*商品封面*/
    private String coverUrl;
    /*商品名称*/
    private String name;
    /*售价*/
    private Double sellPrice;
    /*批发价*/
    private Double purchashPrice;
    /*商品数量*/
    private Long residueNum;
    /*可用库存*/
    private Long residueStoreNum;
    /*商品类型*/
    private Long categoryId;
    private String categoryName;
    /*状态，下架、上架*/
    private String state;
    /*操作者*/
    private String updateby;
    private String info;
    /*操作时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    public Double getPurchashPrice() {
        return purchashPrice;
    }

    public void setPurchashPrice(Double purchashPrice) {
        this.purchashPrice = purchashPrice;
    }

    public Long getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(Long residueNum) {
        this.residueNum = residueNum;
    }

    public Long getResidueStoreNum() {
        return residueStoreNum;
    }

    public void setResidueStoreNum(Long residueStoreNum) {
        this.residueStoreNum = residueStoreNum;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
