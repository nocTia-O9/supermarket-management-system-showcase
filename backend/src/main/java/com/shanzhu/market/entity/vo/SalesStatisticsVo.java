package com.shanzhu.market.entity.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;

/**
 * 商品销售量统计
 */
public class SalesStatisticsVo implements Serializable {
    private Long total; //所有商品总售卖量
    private Page<SaleGoodsVo> vos;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Page<SaleGoodsVo> getVos() {
        return vos;
    }

    public void setVos(Page<SaleGoodsVo> vos) {
        this.vos = vos;
    }
}
