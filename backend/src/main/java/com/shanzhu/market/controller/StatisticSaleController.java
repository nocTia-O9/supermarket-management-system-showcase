package com.shanzhu.market.controller;

import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.query.QueryStatisticSale;
import com.shanzhu.market.entity.vo.SalesStatisticsVo;
import com.shanzhu.market.service.IGoodsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Validated
@RequestMapping("/goods_management/statistic_sale")
public class StatisticSaleController {

    @Resource
    private IGoodsService goodsService;

    @HasPermisson("goods_management:statistic_sale:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryStatisticSale qo) {
        SalesStatisticsVo vo = goodsService.queryPageStatisticSaleByQo(qo);
        return JsonResult.success(vo);
    }

}
