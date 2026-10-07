package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.PointProducts;
import com.shanzhu.market.entity.query.QueryPointProducts;

import java.util.List;
import java.util.Map;

public interface IPointProductsService  extends IService<PointProducts> {
    Page<PointProducts> queryPageByQo(QueryPointProducts qo);

    List<Map<String, Object>> queryOptionGoods();

    void savePointGoods(PointProducts pointProducts,String token);

    void updatePointGoods(PointProducts pointProducts, String token);

    void del(Long id);
}
