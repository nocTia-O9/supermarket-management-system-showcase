package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.Supplier;
import com.shanzhu.market.entity.query.QuerySupplier;
import com.shanzhu.market.service.ISupplierService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Validated
@RequestMapping("/inventory_management/supplier")
public class SupplierController {

    @Resource
    private ISupplierService supplierService;

    @HasPermisson("inventory_management:supplier:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QuerySupplier qo) {
        Page<Supplier> page = supplierService.queryPageByQo(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("inventory_management:supplier:save")
    @PostMapping("/save")
    public JsonResult saveSupplier(Supplier supplier){
        supplierService.saveSupplier(supplier);
        return JsonResult.success();
    }

    /*修改接口*/
    @HasPermisson("inventory_management:supplier:update")
    @PostMapping("/update")
    public JsonResult updateSupplier(Supplier supplier){
        supplierService.updateSupplier(supplier);
        return JsonResult.success();
    }

    @HasPermisson("inventory_management:supplier:update")
    @GetMapping("/queryByCn")
    public JsonResult queryByCn(Long cn){
        return JsonResult.success(supplierService.getById(cn));
    }

    @HasPermisson("inventory_management:supplier:deactivate")
    @PostMapping("/deactivate")
    public JsonResult deactivate(Long cn){
        supplierService.deactivate(cn);
        return JsonResult.success();
    }

}
