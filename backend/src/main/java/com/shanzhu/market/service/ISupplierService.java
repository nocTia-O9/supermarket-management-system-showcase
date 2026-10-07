package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.Supplier;
import com.shanzhu.market.entity.query.QuerySupplier;

import java.util.List;
import java.util.Map;

public interface ISupplierService extends IService<Supplier> {

    void deactivate(Long cn);

    Page<Supplier> queryPageByQo(QuerySupplier qo);

    List<Map<String, Object>> queryOptionsSuppliers();

    void saveSupplier(Supplier supplier);

    void updateSupplier(Supplier supplier);
}
