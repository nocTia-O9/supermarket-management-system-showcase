package com.shanzhu.market.entity.domain;


import java.io.Serializable;

public class NoticeIn implements Serializable {
    private Long id;
    private String name; //商品名
    private Double purchashPrice; //进货价格
    private Long goodsNum;
    private String coverUrl; //商品封面

    public NoticeIn() {
    }

    public NoticeIn(Long id, String name, Double purchashPrice, Long goodsNum, String coverUrl) {
        this.id = id;
        this.name = name;
        this.purchashPrice = purchashPrice;
        this.goodsNum = goodsNum;
        this.coverUrl = coverUrl;
    }

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

    public Double getPurchashPrice() {
        return purchashPrice;
    }

    public void setPurchashPrice(Double purchashPrice) {
        this.purchashPrice = purchashPrice;
    }

    public Long getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Long goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
