package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.GoodsCategory;
import com.shanzhu.market.entity.query.QueryGoodsCategory;

import java.util.List;
import java.util.Map;

public interface IGoodsCategoryService extends IService<GoodsCategory> {

    /**
     * 更改业务
     */
    void updateGoodsCategory(GoodsCategory goodsCategory);

    /**
     * 停用业务
     */
    void deactivate(Long cid);

    /**
     * 条件查询
     */
    Page<GoodsCategory> queryPageByQo(QueryGoodsCategory qo);

    /**
     * 保存商品分类信息
     */
    void saveGoodsCategory(GoodsCategory category);

    List<Map<String, Object>> getNormalCategoryAll();
}
