package com.shanzhu.market.entity.vo;

import java.io.Serializable;

public class GoodsStoreVo implements Serializable {
    private Long id;
    private String name; //商品名
    private String coverUrl; //商品封面
    private Long inventory;//需库存量
    private Long shelves;//货架商品数量

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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public Long getShelves() {
        return shelves;
    }

    public void setShelves(Long shelves) {
        this.shelves = shelves;
    }
}
