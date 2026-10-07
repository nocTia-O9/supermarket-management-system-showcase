package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.ExchangePointProducts;
import com.shanzhu.market.entity.domain.PointProducts;
import com.shanzhu.market.entity.query.QueryExchangePointProductsRecords;

import java.util.List;
import java.util.Map;

public interface IExchangePointProductsService extends IService<ExchangePointProducts> {
    List<Map<String, Object>> queryPointProductBymemberId(Long memberId);

    PointProducts queryPointProductByGoodsId(Long goodsId);

    void saveExchangePointProductRecords(ExchangePointProducts exchangePointProducts, String token);

    List<Map<String, Object>> queryOptionsMemberPhone();

    void delExchangePointProducts(String cn);

    Page<ExchangePointProducts> queryPageByQoExchangePointProducts(QueryExchangePointProductsRecords qo);

    List<Map<String, Object>> queryOptionsPointProducts();

    List<Map<String, Object>> queryOptionsMember();

    List<Map<String, Object>> queryMemberByGoodsId(Long goodsId);
}
