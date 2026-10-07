package com.shanzhu.market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.entity.domain.DetailStoreGoods;
import com.shanzhu.market.entity.domain.Supplier;
import com.shanzhu.market.entity.query.QuerySupplier;
import com.shanzhu.market.mapper.SupplierMapper;
import com.shanzhu.market.service.IDetailStoreGoodsService;
import com.shanzhu.market.service.ISupplierService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements ISupplierService {

    @Resource
    private IDetailStoreGoodsService detailStoreGoodsService;

    @Override
    public void deactivate(Long cn) {
        QueryWrapper<DetailStoreGoods> detailStoreGoodsQueryWrapper = new QueryWrapper<DetailStoreGoods>()
                .eq("state1", DetailStoreGoods.STATE1_NORMAL)
                .eq("type", DetailStoreGoods.TYPE_IN)
                .eq("state", DetailStoreGoods.STATE_NORMAL)
                .eq("supplier_id", cn);
        List<DetailStoreGoods> list = detailStoreGoodsService.list(detailStoreGoodsQueryWrapper);
        if (list!=null&&list.size()>0){
            throw new BusinessException("该供货商正在被入库订单使用，请解除关系之后在停用");
        }
        UpdateWrapper<Supplier> updateWrapper = new UpdateWrapper<Supplier>().set("state", Supplier.STATE_BAN).eq("cn", cn);
        super.update(updateWrapper);
    }

    @Override
    public Page<Supplier> queryPageByQo(QuerySupplier qo) {
        Page<Supplier> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Supplier> wrapper = new QueryWrapper<Supplier>()
                .like(StringUtils.hasText(qo.getName()), "name", qo.getName())
                .like(StringUtils.hasText(qo.getAddress()), "address", qo.getAddress())
                .like(StringUtils.hasText(qo.getInfo()), "info", qo.getInfo())
                .eq("state",Supplier.STATE_NORMAL);
        super.page(page,wrapper);
        return page;
    }

    @Override
    public List<Map<String, Object>> queryOptionsSuppliers() {
        List<Map<String, Object>> list = new ArrayList<>();
        QueryWrapper<Supplier> wrapper = new QueryWrapper<Supplier>().eq("state", Supplier.STATE_NORMAL);
        List<Supplier> suppliers = super.list(wrapper);
        if (suppliers==null ||suppliers.size()<=0){
            return new ArrayList<>();
        }
        for (Supplier supplier : suppliers) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",supplier.getCn());
            map.put("name",supplier.getName());
            list.add(map);
        }
        return list;

    }

    @Override
    public void saveSupplier(Supplier supplier) {
        supplier.setState(Supplier.STATE_NORMAL);
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<Supplier>()
                .eq("name", supplier.getName())
                .eq("state", Supplier.STATE_NORMAL);
        Supplier one = super.getOne(queryWrapper);
        if (one!=null){
            throw new BusinessException("已存在供货商的联系方式");
        }
        super.save(supplier);
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        if (Supplier.STATE_NORMAL.equals(supplier.getState())){
            QueryWrapper<Supplier> queryWrapper = new QueryWrapper<Supplier>()
                    .eq("name", supplier.getName())
                    .eq("state", Supplier.STATE_NORMAL)
                    .ne("cn", supplier.getCn());
            Supplier one = super.getOne(queryWrapper);
            if (one!=null){
                throw new BusinessException("该供货商已存在");
            }
        }
        super.updateById(supplier);
    }
}
