package com.shanzhu.market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.market.entity.domain.Goods;
import com.shanzhu.market.entity.domain.NoticeIn;
import com.shanzhu.market.entity.domain.NoticeOut;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    int getNoticeInTotalCount(Map<String, Object> map);

    List<NoticeIn> getNoticePageList(Map<String, Object> map);

    int getNoticeOutShelvesTotalCount(Map<String, Object> map);

    List<NoticeOut> getNoticeShelvesPageList(Map<String, Object> map);
    Long queryPageStatisticSaleByQo(String name);
}
