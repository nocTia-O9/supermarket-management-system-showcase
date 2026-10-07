package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.GoodsStore;
import com.shanzhu.market.entity.query.QueryDetailStorageSituation;
import com.shanzhu.market.entity.query.QueryStorageSituation;

import java.util.Map;

public interface IGoodsStoreService extends IService<GoodsStore> {


    Long storeUsed(Long id);

    Long getResidueNumByGoodsId(Long id);

    void goodsInStore(Long goodsId, Long goodsNum,Long storeId);

    void goodsOutStore(Long goodsId, Long goodsNum, Long storeId);

    Map<String, Object> queryPageStorageSituationByQo(QueryStorageSituation qo);

    Map<String, Object> queryStoreGoodsByStoreId(QueryDetailStorageSituation qo);
}
