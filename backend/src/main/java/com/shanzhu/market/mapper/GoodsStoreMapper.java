package com.shanzhu.market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.market.entity.domain.GoodsStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsStoreMapper extends BaseMapper<GoodsStore> {
    Long storeUsed(Long id);
    Long getResidueNumByGoodsId(Long goodsId);

    void goodsInStore(@Param("goodsId") Long goodsId,@Param("goodsNum") Long goodsNum,@Param("storeId") Long storeId);

    void goodsOutStore(@Param("goodsId") Long goodsId,@Param("goodsNum") Long goodsNum,@Param("storeId") Long storeId);

    Long totalStoreNum();

    Long getTotalStoreNum1(Long storeId);
}
