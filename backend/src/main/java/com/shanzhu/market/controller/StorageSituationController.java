package com.shanzhu.market.controller;

import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.query.QueryDetailStorageSituation;
import com.shanzhu.market.entity.query.QueryStorageSituation;
import com.shanzhu.market.service.IGoodsStoreService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/inventory_management/store/storage_situation")
public class StorageSituationController {

    @Resource
    private IGoodsStoreService goodsStoreService;

    @HasPermisson("inventory_management:store:storage_situation")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryStorageSituation qo) {
        Map<String, Object> map = goodsStoreService.queryPageStorageSituationByQo(qo);
        return JsonResult.success(map);

    }
    @HasPermisson("inventory_management:store:storage_situation")
    @PostMapping("/queryStoreGoodsByStoreId")
    public JsonResult queryStoreGoodsByStoreId(QueryDetailStorageSituation qo) {
        Map<String, Object> map = goodsStoreService.queryStoreGoodsByStoreId(qo);
        return JsonResult.success(map);

    }

}
