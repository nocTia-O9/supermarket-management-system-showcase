package com.shanzhu.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.common.redis.constants.RedisKeys;
import com.shanzhu.market.common.redis.service.RedisTemplateService;
import com.shanzhu.market.entity.domain.*;
import com.shanzhu.market.entity.query.*;
import com.shanzhu.market.entity.vo.*;
import com.shanzhu.market.mapper.GoodsMapper;
import com.shanzhu.market.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Resource
    private RedisTemplateService redisTemplateService;

    @Resource
    private IGoodsCategoryService goodsCategoryService;

    @Resource
    private IStoreService storeService;

    @Resource
    private IDetailStoreGoodsService detailStoreGoodsService;

    @Resource
    private IGoodsStoreService goodsStoreService;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public Page<GoodsListVo> queryPageByQo(QueryGoods qo) {
        Page<GoodsListVo> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        ArrayList<GoodsListVo> volists = new ArrayList<>();
        Page<Goods> goodsPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>()
                .eq(qo.getId() != null, "id", qo.getId())
                .eq(qo.getSellPrice() != null, "sell_price", qo.getSellPrice())
                .like(StringUtils.hasText(qo.getName()), "name", qo.getName())
                .eq(qo.getCategoryId() != null, "category_id", qo.getCategoryId())
                .eq(StringUtils.hasText(qo.getState()), "state", qo.getState())
                .ge(StringUtils.hasText(qo.getOperateStartTime()), "update_time", qo.getOperateStartTime())
                .le(StringUtils.hasText(qo.getOperateEndTime()), "update_time", qo.getOperateEndTime());
        super.page(goodsPage, wrapper);
        for (Goods record : goodsPage.getRecords()) {
            GoodsListVo vo = new GoodsListVo();
            BeanUtils.copyProperties(record, vo);
            Long residueNum=storeService.getResidueNumByGoodsId(record.getId());
            vo.setResidueStoreNum(residueNum);
            volists.add(vo);
        }
        page.setRecords(volists);
        page.setTotal(goodsPage.getTotal());
        return page;
    }

    @Override
    public void saveGoods(Goods goods, String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        goods.setState(Goods.STATE_UP);
        goods.setCreateby(employee.getNickName());
        goods.setUpdateby(employee.getNickName());
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        if (goods.getCategoryId() != null) {
            /*从缓存中获取分类的信息*/
            if (redisTemplateService.hasKey(RedisKeys.GOODS_CATEGORY.join())) {
                Map<String, Object> categoryCache = redisTemplateService.getCacheMap(RedisKeys.GOODS_CATEGORY.join());
                GoodsCategory category = (GoodsCategory) categoryCache.get(goods.getCategoryId().toString());
                if (category != null) {
                    goods.setCategoryName(category.getName());
                }
            } else {
                GoodsCategory category = goodsCategoryService.getById(goods.getCategoryId());
                if (category != null) {
                    goods.setCategoryName(category.getName());
                }
            }
        }
        super.save(goods);
    }

    @Transactional
    @Override
    public void upOrdown(Long gid, String state,String token) {
        UpdateWrapper<Goods> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", gid);
        if (Goods.STATE_UP.equals(state)) {
            Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
            wrapper.set("state", Goods.STATE_DOWN);
            Goods goods = super.getById(gid);
            QueryWrapper<GoodsStore> goodsStoreQueryWrapper = new QueryWrapper<GoodsStore>().eq("goods_id", gid);
            List<GoodsStore> list = goodsStoreService.list(goodsStoreQueryWrapper);
            for (GoodsStore goodsStore : list) {
                DetailStoreGoods detailStoreGoods = new DetailStoreGoods();
                detailStoreGoods.setCreateid(employee.getId());
                detailStoreGoods.setCreateby(employee.getNickName());
                detailStoreGoods.setCreateTime(new Date());
                detailStoreGoods.setGoodsId(gid);
                detailStoreGoods.setGoodsName(goods.getName());
                detailStoreGoods.setType(DetailStoreGoods.TYPE_IN);
                detailStoreGoods.setState1(DetailStoreGoods.STATE1_UNTREATED);
                detailStoreGoods.setState(DetailStoreGoods.STATE_DOWN);
                detailStoreGoods.setCn(IdWorker.getIdStr());
                detailStoreGoods.setInfo(goods.getName()+"下架处理");
                detailStoreGoods.setGoodsNum(goodsStore.getResidueNum());
                detailStoreGoods.setUntreatedNum(goodsStore.getResidueNum());
                detailStoreGoods.setStoreId(goodsStore.getStoreId());
                detailStoreGoodsService.save(detailStoreGoods);
            }
        } else {
            wrapper.set("residue_num",0);
            wrapper.set("state", Goods.STATE_UP);
            QueryWrapper<DetailStoreGoods> queryWrapper = new QueryWrapper<DetailStoreGoods>().eq("goods_id", gid)
                    .eq("state", DetailStoreGoods.STATE_DOWN)
                    .eq("state1", DetailStoreGoods.STATE1_UNTREATED);
            detailStoreGoodsService.remove(queryWrapper);
        }
        super.update(wrapper);

    }

    @Override
    public void updateGoods(Goods goods, String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        goods.setUpdateby(employee.getNickName());
        goods.setUpdateTime(new Date());
        if (goods.getCategoryId() != null) {
            /*从缓存中获取分类的信息*/
            if (redisTemplateService.hasKey(RedisKeys.GOODS_CATEGORY.join())) {
                Map<String, Object> categoryCache = redisTemplateService.getCacheMap(RedisKeys.GOODS_CATEGORY.join());
                GoodsCategory category = (GoodsCategory) categoryCache.get(goods.getCategoryId().toString());
                if (category != null) {
                    goods.setCategoryName(category.getName());
                }
            } else {
                GoodsCategory category = goodsCategoryService.getById(goods.getCategoryId());
                if (category != null) {
                    goods.setCategoryName(category.getName());
                }
            }
        }
        super.updateById(goods);
    }

    @Override
    public List<Map<String, Object>> selected_goodsAll() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().eq("state", Goods.STATE_UP);
        List<Goods> list = super.list(wrapper);
        if (list==null&&list.size()==0){
            return null;
        }
        List<Map<String, Object>> listVo = new ArrayList<>();
        for (Goods goods : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",goods.getId());
            map.put("name",goods.getName());
            listVo.add(map);
        }

        return listVo;
    }

    @Override
    public List<Map<String, Object>> selected_storeAll() {
        List<Map<String, Object>> list = new ArrayList<>();
        QueryWrapper<Store> wrapper = new QueryWrapper<Store>().eq("state", Store.STATE_NORMAL);
        List<Store> list1 = storeService.list(wrapper);
        if (list1!=null &&list1.size()>0){
            for (Store store : list1) {
                Map<String, Object> map = new HashMap<>();
                map.put("id",store.getId());
                map.put("name",store.getName());
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public void returnGoods(DetailStoreGoods detailStoreGoods, String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        Goods goods = super.getById(detailStoreGoods.getGoodsId());

        /*补全入库订单信息*/
        detailStoreGoods.setCn(IdWorker.getIdStr());
        detailStoreGoods.setCreateby(employee.getNickName());
        detailStoreGoods.setCreateid(employee.getId());
        detailStoreGoods.setType(DetailStoreGoods.TYPE_IN);
        if (DetailStoreGoods.STATE_EXPIRY.equals(detailStoreGoods.getState())){
            //如果是过期，将入库订单的state1修改成2：待处理的状态
            detailStoreGoods.setState1(DetailStoreGoods.STATE1_UNTREATED);
        }else {
            detailStoreGoods.setState1(DetailStoreGoods.STATE1_NORMAL);
        }


        /*获取仓库的信息*/
        QueryWrapper<GoodsStore> goodsStoreQueryWrapper = new QueryWrapper<GoodsStore>()
                .eq("goods_id", detailStoreGoods.getGoodsId())
                .eq("store_id", detailStoreGoods.getStoreId());
        GoodsStore goodsStore = goodsStoreService.getOne(goodsStoreQueryWrapper);
        if (goodsStore==null){
            goodsStore = new GoodsStore();
            goodsStore.setGoodsId(detailStoreGoods.getGoodsId());
            goodsStore.setStoreId(detailStoreGoods.getStoreId());
            Store store = storeService.getById(detailStoreGoods.getStoreId());
            goodsStore.setStoreName(store.getName());
            goodsStore.setInNum(0L);
            goodsStore.setResidueNum(0L);
            goodsStoreService.save(goodsStore);
        }
        long num = goods.getResidueNum() - detailStoreGoods.getGoodsNum();
        if (num>=0){
            //货架还有商品数量
            /*更改商品信息*/
            UpdateWrapper<Goods> goodsUpdateWrapper = new UpdateWrapper<Goods>()
                    .set("residue_num", num)
                    .eq("id", detailStoreGoods.getGoodsId());
            super.update(goodsUpdateWrapper);
            /*更改商品库存信息*/
            UpdateWrapper<GoodsStore> goodsStoreUpdateWrapper = new UpdateWrapper<GoodsStore>()
                    .set("residue_num", goodsStore.getResidueNum() + detailStoreGoods.getGoodsNum())
                    .eq("goods_id", detailStoreGoods.getGoodsId())
                    .eq("store_id", detailStoreGoods.getStoreId());
            goodsStoreService.update(goodsStoreUpdateWrapper);
            detailStoreGoods.setUntreatedNum(detailStoreGoods.getGoodsNum());

        }else {
            //货架没有商品数量
            /*更改商品信息*/
            UpdateWrapper<Goods> goodsUpdateWrapper = new UpdateWrapper<Goods>()
                    .set("residue_num", 0)
                    .eq("id", detailStoreGoods.getGoodsId());
            super.update(goodsUpdateWrapper);
            /*更改商品库存信息*/
            UpdateWrapper<GoodsStore> goodsStoreUpdateWrapper = new UpdateWrapper<GoodsStore>()
                    .set("residue_num", goodsStore.getResidueNum() + goods.getResidueNum())
                    .eq("goods_id", detailStoreGoods.getGoodsId())
                    .eq("store_id", detailStoreGoods.getStoreId());
            goodsStoreService.update(goodsStoreUpdateWrapper);
            detailStoreGoods.setGoodsNum(goods.getResidueNum());
            detailStoreGoods.setUntreatedNum(goods.getResidueNum());
        }
        detailStoreGoodsService.save(detailStoreGoods);
    }

    @Override
    public Page<GoodsStoreVo> queryPageGoodsStore(QueryGoodsStore qo) {
        Page<GoodsStoreVo> page = new Page<>(qo.getCurrentPage(),qo.getPageSize());
        Page<Goods> goodsPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().eq("state", Goods.STATE_UP)
                .like(StringUtils.hasText(qo.getName()), "name", qo.getName());
        super.page(goodsPage,wrapper);
        if (goodsPage.getTotal()<=0) {
            page.setRecords(new ArrayList<>());
            page.setTotal(0);
            return page;
        }
        List<GoodsStoreVo> list = new ArrayList<>();
        for (Goods record : goodsPage.getRecords()) {
            GoodsStoreVo vo = new GoodsStoreVo();
            BeanUtils.copyProperties(record,vo);
            list.add(vo);
        }
        page.setTotal(goodsPage.getTotal());
        page.setRecords(list);
        return page;
    }

    @Override
    public GoodsStoreVo queryGoodsStoreById(Long id) {
        GoodsStoreVo vo = new GoodsStoreVo();
        Goods goods = super.getById(id);
        BeanUtils.copyProperties(goods,vo);
        return vo;
    }

    @Override
    public void updateInventory(GoodsStoreVo vo) {
        if (vo.getInventory()==null){
            vo.setInventory(0L);
        }
        if(vo.getShelves()==null){
            vo.setShelves(0L);
        }
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<Goods>()
                .set("inventory",vo.getInventory())
                .set("shelves",vo.getShelves())
                .eq("id",vo.getId());
        super.update(updateWrapper);

    }

    @Override
    public Page<NoticeIn> queryPageNoticeIn(QueryNoticeIn qo) {
        Page<NoticeIn> noticeInPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        List<NoticeIn> list = new ArrayList<>();
        int start=(qo.getCurrentPage()-1)*qo.getPageSize();
        Map<String, Object> map = new HashMap<>();
        map.put("start",start);
        map.put("size",qo.getPageSize());
        if (StringUtils.hasLength(qo.getName())){
            map.put("name",qo.getName());
        }
        int totalCount=goodsMapper.getNoticeInTotalCount(map);
        list=goodsMapper.getNoticePageList(map);
        noticeInPage.setTotal(totalCount);
        noticeInPage.setRecords(list);
        return noticeInPage;
    }

    @Override
    public Page<NoticeOut> queryPageNoticeOut_shelves(QueryNoticeOut qo) {
        Page<NoticeOut> noticeOutPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        List<NoticeOut> list = new ArrayList<>();
        int start=(qo.getCurrentPage()-1)*qo.getPageSize();
        Map<String, Object> map = new HashMap<>();
        map.put("start",start);
        map.put("size",qo.getPageSize());
        if (StringUtils.hasLength(qo.getName())){
            map.put("name",qo.getName());
        }
        int totalCount=goodsMapper.getNoticeOutShelvesTotalCount(map);
        list=goodsMapper.getNoticeShelvesPageList(map);
        noticeOutPage.setTotal(totalCount);
        noticeOutPage.setRecords(list);
        return noticeOutPage;
    }

    @Override
    public void saveOut_shelves(DetailStoreGoods detailStoreGoods,String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        QueryWrapper<GoodsStore> detailStoreGoodsQueryWrapper = new QueryWrapper<GoodsStore>().eq("goods_id", detailStoreGoods.getGoodsId())
                .eq("store_id", detailStoreGoods.getStoreId());
        GoodsStore goodsStore = goodsStoreService.getOne(detailStoreGoodsQueryWrapper);
        if (goodsStore==null || goodsStore.getResidueNum()==null ||goodsStore.getResidueNum()==0){
            throw new BusinessException("出库失败，库存中没有该商品的库存");
        }
        /*补全出库单的信息*/
        detailStoreGoods.setCn(IdWorker.getIdStr());
        detailStoreGoods.setCreateby(employee.getNickName());
        detailStoreGoods.setCreateid(employee.getId());
        detailStoreGoods.setType(DetailStoreGoods.TYPE_OUT);
        detailStoreGoods.setState1(DetailStoreGoods.STATE1_NORMAL);
        long num = goodsStore.getResidueNum() - detailStoreGoods.getGoodsNum();
        Goods goods = super.getById(detailStoreGoods.getGoodsId());
        if (num>=0){
            /*修改货架商品数量*/
            UpdateWrapper<Goods> goodsUpdateWrapper = new UpdateWrapper<Goods>()
                    .set("residue_num", goods.getResidueNum() == null ? detailStoreGoods.getGoodsNum() : goods.getResidueNum() + detailStoreGoods.getGoodsNum())
                    .eq("id",detailStoreGoods.getGoodsId());
            super.update(goodsUpdateWrapper);
            /*修改商品库存数量*/
            UpdateWrapper<GoodsStore> goodsStoreUpdateWrapper = new UpdateWrapper<GoodsStore>()
                    .set("residue_num", goodsStore.getResidueNum() - detailStoreGoods.getGoodsNum())
                    .eq("goods_id", detailStoreGoods.getGoodsId())
                    .eq("store_id", detailStoreGoods.getStoreId());
            goodsStoreService.update(goodsStoreUpdateWrapper);
            /*添加出库记录*/
            detailStoreGoodsService.save(detailStoreGoods);
        }else {
            /*修改货架商品数量*/
            UpdateWrapper<Goods> goodsUpdateWrapper = new UpdateWrapper<Goods>()
                    .set("residue_num", goods.getResidueNum() == null ? goodsStore.getResidueNum() : goods.getResidueNum() + goodsStore.getResidueNum())
                    .eq("id",detailStoreGoods.getGoodsId());
            super.update(goodsUpdateWrapper);
            /*修改商品库存数量*/
            UpdateWrapper<GoodsStore> goodsStoreUpdateWrapper = new UpdateWrapper<GoodsStore>()
                    .set("residue_num",0L)
                    .eq("goods_id", detailStoreGoods.getGoodsId())
                    .eq("store_id", detailStoreGoods.getStoreId());
            goodsStoreService.update(goodsStoreUpdateWrapper);
            /*添加出库记录*/
            detailStoreGoods.setGoodsNum(goodsStore.getResidueNum());
            detailStoreGoodsService.save(detailStoreGoods);
        }


    }

    @Override
    public SalesStatisticsVo queryPageStatisticSaleByQo(QueryStatisticSale qo) {
       Long total=goodsMapper.queryPageStatisticSaleByQo(qo.getName());
        SalesStatisticsVo vo = new SalesStatisticsVo();
        vo.setTotal(total);
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().eq("state", Goods.STATE_UP)
                .like(StringUtils.hasText(qo.getName()), "name", qo.getName());
        Page<Goods> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        super.page(page,wrapper);
        Page<SaleGoodsVo> saleGoodsVoPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        saleGoodsVoPage.setTotal(page.getTotal());
        List<SaleGoodsVo> saleGoodsVos = new ArrayList<>();
        for (Goods record : page.getRecords()) {
            SaleGoodsVo goodsVo = new SaleGoodsVo();
            goodsVo.setGoodsId(record.getId());
            goodsVo.setGoodsName(record.getName());
            goodsVo.setSalesVolume(record.getSalesVolume());
            goodsVo.setPercentage(total);
            goodsVo.setCoverUrl(record.getCoverUrl());
            saleGoodsVos.add(goodsVo);
        }
        saleGoodsVoPage.setRecords(saleGoodsVos);
        vo.setVos(saleGoodsVoPage);
        return vo;
    }

    @Override
    public Page<NoticeInNotNormalVo> queryPageNoticeOut_untreated(QueryNoticeOut qo) {
        Page<NoticeInNotNormalVo> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        List<NoticeInNotNormalVo> vos = new ArrayList<>();
        QueryWrapper<DetailStoreGoods> queryWrapper = new QueryWrapper<DetailStoreGoods>().eq("state1", DetailStoreGoods.STATE1_UNTREATED);
        queryWrapper.eq(StringUtils.hasText(qo.getState()),"state",qo.getState());
        queryWrapper.like(StringUtils.hasText(qo.getName()),"goods_name",qo.getName());
        queryWrapper.eq("type",DetailStoreGoods.TYPE_IN);
        queryWrapper.orderByDesc("create_time");
        List<DetailStoreGoods> list = detailStoreGoodsService.list(queryWrapper);
        for (DetailStoreGoods detailStoreGoods : list) {
            NoticeInNotNormalVo vo = new NoticeInNotNormalVo();
            vo.setCn(detailStoreGoods.getCn());
            vo.setCreateTime(detailStoreGoods.getCreateTime());
            vo.setGoodsId(detailStoreGoods.getGoodsId());
            vo.setGoodsName(detailStoreGoods.getGoodsName());
            vo.setUntreatedNum(detailStoreGoods.getUntreatedNum());
            vo.setState(detailStoreGoods.getState());
            vo.setStoreId(detailStoreGoods.getStoreId());
            Store store = storeService.getById(detailStoreGoods.getStoreId());
            vo.setStoreName(store.getName());
            Goods goods = super.getById(detailStoreGoods.getGoodsId());
            vo.setCoverUrl(goods.getCoverUrl());
            vos.add(vo);

        }
        page.setRecords(vos);
        return page;
    }

    @Override
    public void resolveOutUntreatedForm(NoticeInNotNormalVo vo, String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        QueryWrapper<DetailStoreGoods> queryWrapper = new QueryWrapper<DetailStoreGoods>()
                .eq("cn", vo.getCn())
                .eq("state1", DetailStoreGoods.STATE1_UNTREATED);
        DetailStoreGoods detailStoreGoods = detailStoreGoodsService.getOne(queryWrapper);
        if (detailStoreGoods==null){
            throw new BusinessException("该订单已被处理");
        }

        long num = detailStoreGoods.getUntreatedNum() - vo.getUntreatedNum();
        QueryWrapper<GoodsStore> goodsStoreQueryWrapper = new QueryWrapper<GoodsStore>()
                .eq("goods_id", vo.getGoodsId())
                .eq("store_id", vo.getStoreId());
        GoodsStore goodsStore = goodsStoreService.getOne(goodsStoreQueryWrapper);
        if (num>0){
            //未处理完毕
            UpdateWrapper<DetailStoreGoods> updateWrapper = new UpdateWrapper<DetailStoreGoods>()
                    .eq("cn", detailStoreGoods.getCn())
                    .set("untreated_num",num);
            detailStoreGoodsService.update(updateWrapper);
            //改变库存
            UpdateWrapper<GoodsStore> goodsStoreUpdateWrapper = new UpdateWrapper<GoodsStore>()
                    .eq("goods_id", vo.getGoodsId())
                    .eq("store_id", vo.getStoreId())
                    .set("residue_num",goodsStore.getResidueNum()-vo.getUntreatedNum());
            goodsStoreService.update(goodsStoreUpdateWrapper);
        }else {
            //处理完毕
            UpdateWrapper<DetailStoreGoods> updateWrapper = new UpdateWrapper<DetailStoreGoods>()
                    .eq("cn", detailStoreGoods.getCn())
                    .set("untreated_num",0L)
                    .set("state1",DetailStoreGoods.STATE1_NORMAL)
                    .set("createid",employee.getId())
                    .set("createby",employee.getNickName())
                    .set("create_time",new Date())
                    .set("type",DetailStoreGoods.TYPE_OUT);
            detailStoreGoodsService.update(updateWrapper);
            //改变库存
            UpdateWrapper<GoodsStore> goodsStoreUpdateWrapper = new UpdateWrapper<GoodsStore>()
                    .eq("goods_id", vo.getGoodsId())
                    .eq("store_id", vo.getStoreId())
                    .set("residue_num",0L);
            goodsStoreService.update(goodsStoreUpdateWrapper);
        }

    }

}
