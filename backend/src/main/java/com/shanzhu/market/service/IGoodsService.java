package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.DetailStoreGoods;
import com.shanzhu.market.entity.domain.Goods;
import com.shanzhu.market.entity.domain.NoticeIn;
import com.shanzhu.market.entity.domain.NoticeOut;
import com.shanzhu.market.entity.query.*;
import com.shanzhu.market.entity.vo.GoodsListVo;
import com.shanzhu.market.entity.vo.GoodsStoreVo;
import com.shanzhu.market.entity.vo.NoticeInNotNormalVo;
import com.shanzhu.market.entity.vo.SalesStatisticsVo;

import java.util.List;
import java.util.Map;

public interface IGoodsService extends IService<Goods> {

    /**
     * 分页查询出数据
     */
    Page<GoodsListVo> queryPageByQo(QueryGoods qo);

    void saveGoods(Goods goods, String token);

    void upOrdown(Long gid, String state,String token);

    void updateGoods(Goods goods, String token);

    List<Map<String, Object>> selected_goodsAll();

    List<Map<String, Object>> selected_storeAll();

    void returnGoods(DetailStoreGoods detailStoreGoods, String token);

    Page<GoodsStoreVo> queryPageGoodsStore(QueryGoodsStore qo);

    GoodsStoreVo queryGoodsStoreById(Long id);

    void updateInventory(GoodsStoreVo vo);

    Page<NoticeIn> queryPageNoticeIn(QueryNoticeIn qo);

    Page<NoticeOut> queryPageNoticeOut_shelves(QueryNoticeOut qo);

    void saveOut_shelves(DetailStoreGoods detailStoreGoods,String token);

    SalesStatisticsVo queryPageStatisticSaleByQo(QueryStatisticSale qo);

    Page<NoticeInNotNormalVo> queryPageNoticeOut_untreated(QueryNoticeOut qo);

    void resolveOutUntreatedForm(NoticeInNotNormalVo vo, String token);
}
