package com.shanzhu.market.entity.domain;

import java.io.Serializable;

public class NoticeOut implements Serializable {
    private Long id;
    private String name; //商品名
    private Long goodsNum;
    private String coverUrl; //商品封面
    private String state;

    public NoticeOut() {
    }

    public NoticeOut(Long id, String name, Long goodsNum, String coverUrl, String state) {
        this.id = id;
        this.name = name;
        this.goodsNum = goodsNum;
        this.coverUrl = coverUrl;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
