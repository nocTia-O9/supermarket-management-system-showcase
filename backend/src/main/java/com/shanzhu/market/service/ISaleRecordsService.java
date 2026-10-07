package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.SaleRecords;
import com.shanzhu.market.entity.query.QuerySaleRecords;

import java.util.List;
import java.util.Map;

public interface ISaleRecordsService extends IService<SaleRecords> {
    List<Map<String, Object>> getOptionSaleRecordsGoods();

    SaleRecords saveSaleRecords(SaleRecords saleRecords, String token);

    Page<SaleRecords> queryPageByQoSaleRecords(QuerySaleRecords qo);

    void delSaleRecords(String cn);
}
