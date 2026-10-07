package com.shanzhu.market.entity.vo;

import java.io.Serializable;

/**
 * 仓库存储情况
 */
public class DetailStorageSituationVo implements Serializable {
    private Long goodsId;
    private String goodsName;
    private Long residueNum; //商品数量
    private Long percentage=0L;
    public void setPercentage(Long total) {
        if (total==null ||total==0){
            this.percentage=0L;
        }else {
            String num=((this.residueNum*100.0)/total)+"";
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

    public Long getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(Long residueNum) {
        this.residueNum = residueNum;
    }

    public Long getPercentage() {
        return percentage;
    }
}
