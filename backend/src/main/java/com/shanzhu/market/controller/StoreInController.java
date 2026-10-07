package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.DetailStoreGoods;
import com.shanzhu.market.entity.query.QueryDetailStoreGoods;
import com.shanzhu.market.entity.vo.DetailStoreGoodsVo;
import com.shanzhu.market.service.IDetailStoreGoodsService;
import com.shanzhu.market.service.ISupplierService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/inventory_management/detail_store_goods_in")
public class StoreInController {

    @Resource
    private IDetailStoreGoodsService detailStoreGoodsService;

    @Resource
    private ISupplierService supplierService;

    @HasPermisson("inventory_management:detail_store_goods_in:save")
    @PostMapping("/save")
    public JsonResult saveIn(DetailStoreGoods detailStoreGoods, HttpServletRequest request) {
        detailStoreGoodsService.saveIn(detailStoreGoods, (String) request.getHeader("token"));
        return JsonResult.success();
    }

    @HasPermisson("inventory_management:detail_store_goods_in:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryDetailStoreGoods qo) {
        Page<DetailStoreGoodsVo> page = detailStoreGoodsService.queryPageByQoIn(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("inventory_management:detail_store_goods_in:delIn")
    @PostMapping("/delIn")
    public JsonResult delIn(@NotEmpty(message = "系统编号不能为空") String cn) {
        detailStoreGoodsService.delIn(cn);
        return JsonResult.success();
    }
    @HasPermisson("inventory_management:detail_store_goods_in:save")
    @GetMapping("/queryOptionsSuppliers")
    public JsonResult queryOptionsSuppliers() {
        List<Map<String, Object>> list = supplierService.queryOptionsSuppliers();
        return JsonResult.success(list);
    }
}
