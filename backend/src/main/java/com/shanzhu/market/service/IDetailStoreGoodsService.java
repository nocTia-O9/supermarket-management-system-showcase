package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.DetailStoreGoods;
import com.shanzhu.market.entity.query.QueryDetailStoreGoods;
import com.shanzhu.market.entity.query.QueryDetailStoreGoodsOut;
import com.shanzhu.market.entity.vo.DetailStoreGoodsOutVo;
import com.shanzhu.market.entity.vo.DetailStoreGoodsVo;

import java.util.List;
import java.util.Map;

public interface IDetailStoreGoodsService extends IService<DetailStoreGoods> {

    void saveIn(DetailStoreGoods detailStoreGoods, String token);

    Page<DetailStoreGoodsVo> queryPageByQoIn(QueryDetailStoreGoods qo);

    void delIn(String cn);

    Page<DetailStoreGoodsOutVo> queryPageByQoOut(QueryDetailStoreGoodsOut qo);

    Map<String ,Object> initOutOptions();

    List<Map<String,Object>> changeOutGoods(Long gid);

    List<Map<String,Object>> changeOutStore(Long storeId);

    DetailStoreGoodsOutVo queryOutGoods(Long goodsId, Long storeId);

    void saveOut(DetailStoreGoods detailStoreGoods, String token);
}
