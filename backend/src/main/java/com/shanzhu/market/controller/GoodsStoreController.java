package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.query.QueryGoodsStore;
import com.shanzhu.market.entity.vo.GoodsStoreVo;
import com.shanzhu.market.service.IGoodsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Validated
@RequestMapping("/goods_management/goods_store")
public class GoodsStoreController {

    @Resource
    private IGoodsService goodsService;

    /*查询信息*/
    @HasPermisson("goods_management:goods_store:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryGoodsStore qo) {
        Page<GoodsStoreVo> page = goodsService.queryPageGoodsStore(qo);
        return JsonResult.success(page);
    }

    @GetMapping("/queryGoodsStoreById")
    public JsonResult queryGoodsStoreById(Long id) {
        GoodsStoreVo vo= goodsService.queryGoodsStoreById(id);
        return JsonResult.success(vo);
    }

    @PostMapping("/updateInventory")
    public JsonResult updateInventory(GoodsStoreVo vo) {
         goodsService.updateInventory(vo);
        return JsonResult.success();
    }

}
