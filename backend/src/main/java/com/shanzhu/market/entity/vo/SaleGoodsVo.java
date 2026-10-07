package com.shanzhu.market.entity.vo;

import java.io.Serializable;

/**
 * 单个商品销售信息
 */
public class SaleGoodsVo implements Serializable {
    private Long goodsId;
    private String goodsName;
    private String coverUrl;
    private Long salesVolume;//销量
    private Long percentage=0L;
    public void setPercentage(Long total) {
        if (total==null || total==0){
            this.percentage=0L;
        }else {
            if (this.salesVolume==null){
                this.salesVolume=0L;
            }
            String num=((this.salesVolume*100.0)/total)+"";
            Long num1=Long.valueOf(num.split("\\.")[0]);
            this.percentage =num1;
        }

    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Long getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Long getPercentage() {
        return percentage;
    }
}
