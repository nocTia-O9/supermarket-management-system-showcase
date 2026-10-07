package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_goods_store")
public class GoodsStore implements Serializable {
    @TableField("goods_id")
    private Long goodsId;
    @TableField("store_id")
    private Long storeId;
    @TableField("in_num")
    private Long inNum;
    @TableField("residue_num")
    private Long residueNum;
    @TableField("store_name")
    private String storeName;

    public GoodsStore() {
    }

    public GoodsStore(Long goodsId, Long storeId, Long inNum, Long residueNum, String storeName) {
        this.goodsId = goodsId;
        this.storeId = storeId;
        this.inNum = inNum;
        this.residueNum = residueNum;
        this.storeName = storeName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getInNum() {
        return inNum;
    }

    public void setInNum(Long inNum) {
        this.inNum = inNum;
    }

    public Long getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(Long residueNum) {
        this.residueNum = residueNum;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
