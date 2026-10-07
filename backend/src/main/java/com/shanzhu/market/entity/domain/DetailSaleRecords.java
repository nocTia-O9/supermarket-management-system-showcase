package com.shanzhu.market.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("detail_sale_records")
public class DetailSaleRecords implements Serializable {
    @TableField("sell_cn")
    private String sellCn;
    @TableField("goods_id")
    private Long goodsId;
    @TableField("goods_num")
    private Long goodsNum;
    @TableField("goods_price")
    private Double goodsPrice;
    @TableField("goods_name")
    private String goodsName;

    public DetailSaleRecords() {
    }

    public DetailSaleRecords(String sellCn, Long goodsId, Long goodsNum, Double goodsPrice, String goodsName) {
        this.sellCn = sellCn;
        this.goodsId = goodsId;
        this.goodsNum = goodsNum;
        this.goodsPrice = goodsPrice;
        this.goodsName = goodsName;
    }

    public String getSellCn() {
        return sellCn;
    }

    public void setSellCn(String sellCn) {
        this.sellCn = sellCn;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Long goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
