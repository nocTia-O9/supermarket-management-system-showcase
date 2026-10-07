package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.Store;
import com.shanzhu.market.entity.query.QueryStore;
import com.shanzhu.market.service.IStoreService;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Validated
@RequestMapping("/inventory_management/store")
public class StoreController {

    @Resource
    private IStoreService storeService;

    /*保存仓库信息接口*/
    @HasPermisson("inventory_management:store:save")
    @PostMapping("/save")
    public JsonResult saveStore(Store store){
        storeService.saveStore(store);
        return JsonResult.success();
    }
    /*修改仓库接口*/
    @HasPermisson("inventory_management:store:update")
    @PostMapping("/update")
    public JsonResult updateStore(Store store){
        storeService.updateStore(store);
        return JsonResult.success();
    }
    /*停用仓库*/
    @HasPermisson("inventory_management:store:deactivate")
    @PostMapping("/deactivate")
    public JsonResult deactivate(Long sid){
        storeService.deactivate(sid);
        return JsonResult.success();
    }
    /*查询仓库信息*/
    @HasPermisson("inventory_management:store:list")
    @PostMapping("/list")
    public JsonResult list(QueryStore qo){
        return JsonResult.success(storeService.list( new QueryWrapper<Store>()
                .like(StringUtils.hasText(qo.getName()),"name",qo.getName())
                .eq(StringUtils.hasText(qo.getState()),"state",qo.getState())
        ));
    }

}
