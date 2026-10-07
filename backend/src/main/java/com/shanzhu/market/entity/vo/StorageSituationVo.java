package com.shanzhu.market.entity.vo;

import java.io.Serializable;

/**
 * 仓库存储情况
 */
public class StorageSituationVo implements Serializable {
    private Long storeId;
    private String storeName;
    private Long residueNum; //该仓库存储商品数量

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(Long residueNum) {
        this.residueNum = residueNum;
    }
}
