package com.shanzhu.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.common.redis.service.RedisTemplateService;
import com.shanzhu.market.entity.domain.*;
import com.shanzhu.market.entity.query.QueryDetailStoreGoods;
import com.shanzhu.market.entity.query.QueryDetailStoreGoodsOut;
import com.shanzhu.market.entity.vo.DetailStoreGoodsOutVo;
import com.shanzhu.market.entity.vo.DetailStoreGoodsVo;
import com.shanzhu.market.mapper.DetailStoreGoodsMapper;
import com.shanzhu.market.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class DetailStoreGoodsServiceImpl extends ServiceImpl<DetailStoreGoodsMapper, DetailStoreGoods> implements IDetailStoreGoodsService {

    @Resource
    private RedisTemplateService redisTemplateService;

    @Resource
    private IGoodsStoreService goodsStoreService;

    @Resource
    private IStoreService storeService;

    @Resource
    private IGoodsService goodsService;

    @Resource
    private ISupplierService supplierService;

    @Override
    public void saveIn(DetailStoreGoods detailStoreGoods, String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        detailStoreGoods.setType(DetailStoreGoods.TYPE_IN);
        detailStoreGoods.setState(DetailStoreGoods.STATE_NORMAL);
        detailStoreGoods.setCreateid(employee.getId());
        detailStoreGoods.setCreateby(employee.getNickName());
        detailStoreGoods.setCn(IdWorker.getIdStr());
        detailStoreGoods.setCreateTime(new Date());
        detailStoreGoods.setState1(DetailStoreGoods.STATE1_NORMAL);
        goodsStoreService.goodsInStore(detailStoreGoods.getGoodsId(), detailStoreGoods.getGoodsNum(), detailStoreGoods.getStoreId());
        Supplier supplier = supplierService.getById(detailStoreGoods.getSupplierId());
        detailStoreGoods.setSupplierName(supplier.getName());
        super.save(detailStoreGoods);
    }

    @Override
    public Page<DetailStoreGoodsVo> queryPageByQoIn(QueryDetailStoreGoods qo) {
        Page<DetailStoreGoods> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        Page<DetailStoreGoodsVo> page1 = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<DetailStoreGoods> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.hasText(qo.getCn()), "cn", qo.getCn());
        wrapper.like(StringUtils.hasText(qo.getGoodsName()), "goods_name", qo.getGoodsName());
        wrapper.eq(StringUtils.hasText(qo.getState1()), "state1", qo.getState1());
        wrapper.ge(StringUtils.hasText(qo.getStartCreateTime()), "create_time", qo.getStartCreateTime());
        wrapper.le(StringUtils.hasText(qo.getEndCreateTime()), "create_time", qo.getEndCreateTime());
        wrapper.eq("type", DetailStoreGoods.TYPE_IN);
        super.page(page, wrapper);
        List<DetailStoreGoods> records = page.getRecords();
        if (records == null || records.size() <= 0) {
            page1.setTotal(0L);
        }
        List<DetailStoreGoodsVo> list = new ArrayList<>();
        for (DetailStoreGoods record : records) {
            DetailStoreGoodsVo vo = new DetailStoreGoodsVo();
            BeanUtils.copyProperties(record, vo);
            Store store = storeService.getById(record.getStoreId());
            if (store != null) {
                vo.setStoreName(store.getName());
            }
            list.add(vo);
        }
        page1.setRecords(list);
        page1.setTotal(page.getTotal());
        return page1;
    }

    @Override
    public void delIn(String cn) {
        UpdateWrapper<DetailStoreGoods> wrapper = new UpdateWrapper<>();
        wrapper.set("state1", DetailStoreGoods.STATE1_DEL);
        wrapper.eq("cn", cn);
        super.update(wrapper);
    }

    @Override
    public Page<DetailStoreGoodsOutVo> queryPageByQoOut(QueryDetailStoreGoodsOut qo) {
        Page<DetailStoreGoods> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        Page<DetailStoreGoodsOutVo> page1 = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<DetailStoreGoods> wrapper = new QueryWrapper<>();
        wrapper.likeRight(StringUtils.hasText(qo.getCn()), "cn", qo.getCn());
        wrapper.like(StringUtils.hasText(qo.getGoodsName()), "goods_name", qo.getGoodsName());
        wrapper.ge(StringUtils.hasText(qo.getStartCreateTime()), "create_time", qo.getStartCreateTime());
        wrapper.le(StringUtils.hasText(qo.getEndCreateTime()), "create_time", qo.getEndCreateTime());
        wrapper.eq("type", DetailStoreGoods.TYPE_OUT);
        wrapper.eq(StringUtils.hasText(qo.getState()), "state", qo.getState());
        wrapper.eq(StringUtils.hasText(qo.getState1()), "state1", qo.getState1());
        super.page(page, wrapper);
        List<DetailStoreGoods> records = page.getRecords();
        if (records == null || records.size() <= 0) {
            page1.setTotal(0L);
        }
        List<DetailStoreGoodsOutVo> list = new ArrayList<>();
        for (DetailStoreGoods record : records) {
            DetailStoreGoodsOutVo vo = new DetailStoreGoodsOutVo();
            BeanUtils.copyProperties(record, vo);
            Store store = storeService.getById(record.getStoreId());
            if (store != null) {
                vo.setStoreName(store.getName());
            }
            list.add(vo);
        }
        page1.setRecords(list);
        page1.setTotal(page.getTotal());
        return page1;
    }

    @Override
    public Map<String, Object> initOutOptions() {
        Set<Long> goodsIds = new HashSet<>();
        Set<Long> storeIds = new HashSet<>();
        QueryWrapper<GoodsStore> wrapper = new QueryWrapper<>();
        wrapper.gt("residue_num", 0L);
        List<GoodsStore> list = goodsStoreService.list(wrapper);
        if (list == null || list.size() == 0) {
            throw new BusinessException("库存中没有存放商品");
        }
        for (GoodsStore goodsStore : list) {
            goodsIds.add(goodsStore.getGoodsId());
            storeIds.add(goodsStore.getStoreId());
        }
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> goodsList = new ArrayList<>();
        List<Map<String, Object>> storeList = new ArrayList<>();
        List<Goods> goods = goodsService.listByIds(goodsIds);
        for (Goods good : goods) {
            Map<String, Object> goodsMap = new HashMap<>();
            goodsMap.put("id", good.getId());
            goodsMap.put("name", good.getName());
            goodsList.add(goodsMap);
        }
        List<Store> stores = storeService.listByIds(storeIds);
        for (Store store : stores) {
            Map<String, Object> storeMap = new HashMap<>();
            storeMap.put("id", store.getId());
            storeMap.put("name", store.getName());
            storeList.add(storeMap);
        }
        map.put("goods", goodsList);
        map.put("stores", storeList);
        return map;
    }

    @Override
    public List<Map<String, Object>> changeOutGoods(Long gid) {
        QueryWrapper<GoodsStore> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id", gid);
        wrapper.gt("residue_num", 0L);
        List<GoodsStore> list = goodsStoreService.list(wrapper);
        Set<Long> storeIds = new HashSet<>();
        for (GoodsStore goodsStore : list) {
            storeIds.add(goodsStore.getStoreId());
        }
        List<Store> stores = storeService.listByIds(storeIds);
        List<Map<String, Object>> storeList = new ArrayList<>();
        for (Store store : stores) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", store.getId());
            map.put("name", store.getName());
            storeList.add(map);
        }
        return storeList;
    }

    @Override
    public List<Map<String, Object>> changeOutStore(Long storeId) {
        QueryWrapper<GoodsStore> wrapper = new QueryWrapper<>();
        wrapper.eq("store_id", storeId);
        wrapper.gt("residue_num", 0L);
        List<GoodsStore> list = goodsStoreService.list(wrapper);
        Set<Long> goodsIds = new HashSet<>();
        for (GoodsStore goodsStore : list) {
            goodsIds.add(goodsStore.getGoodsId());
        }
        List<Goods> goodsList = goodsService.listByIds(goodsIds);
        List<Map<String, Object>> goodsVo = new ArrayList<>();
        for (Goods goods : goodsList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", goods.getId());
            map.put("name", goods.getName());
            goodsVo.add(map);
        }
        return goodsVo;
    }

    @Override
    public DetailStoreGoodsOutVo queryOutGoods(Long goodsId, Long storeId) {
        QueryWrapper<GoodsStore> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",goodsId);
        wrapper.eq("store_id",storeId);
        GoodsStore goodsStore = goodsStoreService.getOne(wrapper);
        DetailStoreGoodsOutVo vo = new DetailStoreGoodsOutVo();
        BeanUtils.copyProperties(goodsStore,vo);
        vo.setGoodsNum(goodsStore.getResidueNum());
        Goods goods = goodsService.getById(goodsId);
        vo.setGoodsName(goods.getName());
        return vo;
    }

    @Override
    public void saveOut(DetailStoreGoods detailStoreGoods, String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        detailStoreGoods.setType(DetailStoreGoods.TYPE_OUT);
        detailStoreGoods.setState1(DetailStoreGoods.STATE1_NORMAL);
        detailStoreGoods.setCreateid(employee.getId());
        detailStoreGoods.setCreateby(employee.getNickName());
        detailStoreGoods.setCn(IdWorker.getIdStr());
        detailStoreGoods.setCreateTime(new Date());

        QueryWrapper<GoodsStore> goodsStoreQueryWrapper = new QueryWrapper<GoodsStore>()
                .eq("goods_id", detailStoreGoods.getGoodsId())
                .eq("store_id", detailStoreGoods.getStoreId());

        GoodsStore goodsStore = goodsStoreService.getOne(goodsStoreQueryWrapper);
        long num = goodsStore.getResidueNum() - detailStoreGoods.getGoodsNum();

        UpdateWrapper<GoodsStore> goodsStoreUpdateWrapper = new UpdateWrapper<GoodsStore>()
                .eq("goods_id", detailStoreGoods.getGoodsId())
                .eq("store_id", detailStoreGoods.getStoreId());
        if (DetailStoreGoods.STATE_EXPIRY.equals(detailStoreGoods.getState())){
            //过期处理
            detailStoreGoods.setState(DetailStoreGoods.STATE_EXPIRY);
            if (num>=0){
                goodsStoreUpdateWrapper.set("residue_num",num);

            }else {
                goodsStoreUpdateWrapper.set("residue_num",0L);
                detailStoreGoods.setGoodsNum(goodsStore.getResidueNum());
            }
        }else {
            //出库到货架上
            detailStoreGoods.setState(DetailStoreGoods.STATE_NORMAL);
            Goods goods = goodsService.getById(detailStoreGoods.getGoodsId());
            UpdateWrapper<Goods> goodsUpdateWrapper = new UpdateWrapper<Goods>().eq("id", goods.getId());
            if (num>=0){
                goodsStoreUpdateWrapper.set("residue_num",num);
                goodsUpdateWrapper.set("residue_num",goods.getResidueNum()==null?detailStoreGoods.getGoodsNum():goods.getResidueNum()+detailStoreGoods.getGoodsNum());
            }else {
                goodsStoreUpdateWrapper.set("residue_num",0L);
                goodsUpdateWrapper.set("residue_num",goods.getResidueNum()==null?goodsStore.getResidueNum():goods.getResidueNum()+goodsStore.getResidueNum());
                detailStoreGoods.setGoodsNum(goodsStore.getResidueNum());
            }
            goodsService.update(goodsUpdateWrapper);
        }
        goodsStoreService.update(goodsStoreUpdateWrapper);
        super.save(detailStoreGoods);
    }
}
