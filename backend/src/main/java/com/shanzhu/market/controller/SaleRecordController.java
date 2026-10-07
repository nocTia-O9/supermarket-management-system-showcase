package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.SaleRecords;
import com.shanzhu.market.entity.query.QuerySaleRecords;
import com.shanzhu.market.service.ISaleRecordsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/sale_management/sale_record")
public class SaleRecordController {

    @Resource
    private ISaleRecordsService saleRecordsService;

    @GetMapping("/getCn")
    public JsonResult getCn(){
        return JsonResult.success(IdWorker.getIdStr());
    }

    @GetMapping("/getOptionSaleRecordsGoods")
    public JsonResult getOptionSaleRecordsGoods(){
        List<Map<String,Object>> list=saleRecordsService.getOptionSaleRecordsGoods();
        return JsonResult.success(list);
    }

    @PostMapping("/saveSaleRecords")
    public JsonResult saveSaleRecords(@RequestBody SaleRecords saleRecords, HttpServletRequest request){
        saleRecords=saleRecordsService.saveSaleRecords(saleRecords,(String) request.getHeader("token"));
        return JsonResult.success(saleRecords);
    }

    @HasPermisson("sale_management:sale_records:list")
    @PostMapping("/queryPageByQoSaleRecords")
    public JsonResult queryPageByQoSaleRecords(QuerySaleRecords qo){
        Page<SaleRecords> page=saleRecordsService.queryPageByQoSaleRecords(qo);
        return JsonResult.success(page);
    }

    @GetMapping("/delSaleRecords")
    public JsonResult delSaleRecords(String cn){
        saleRecordsService.delSaleRecords(cn);
        return JsonResult.success();
    }

}
