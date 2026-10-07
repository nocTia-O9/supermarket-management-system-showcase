package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.Store;

public interface IStoreService extends IService<Store> {

    /**
     * 更改仓库信息的业务
     */
    void updateStore(Store store);

    /**
     * 停用仓库业务
     */
    void deactivate(Long sid);

    Long getResidueNumByGoodsId(Long id);

    void saveStore(Store store);

}
