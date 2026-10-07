package com.shanzhu.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.common.redis.service.RedisTemplateService;
import com.shanzhu.market.entity.domain.Employee;
import com.shanzhu.market.entity.domain.Goods;
import com.shanzhu.market.entity.domain.PointProducts;
import com.shanzhu.market.entity.query.QueryPointProducts;
import com.shanzhu.market.mapper.PointProductsMapper;
import com.shanzhu.market.service.IGoodsService;
import com.shanzhu.market.service.IPointProductsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PointProductsServiceImpl extends ServiceImpl<PointProductsMapper, PointProducts> implements IPointProductsService {

    @Resource
    private IGoodsService goodsService;

    @Resource
    private RedisTemplateService redisTemplateService;

    @Override
    public Page<PointProducts> queryPageByQo(QueryPointProducts qo) {
        Page<PointProducts> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<PointProducts> wrapper = new QueryWrapper<PointProducts>().like(StringUtils.hasText(qo.getName()), "goods_name", qo.getName());
        super.page(page, wrapper);
        return page;
    }

    @Override
    public List<Map<String, Object>> queryOptionGoods() {
        QueryWrapper<PointProducts> pointProductsQueryWrapper = new QueryWrapper<PointProducts>().select("goods_id");
        List<PointProducts> list = super.list();
        Set<Long> productGoodsIds = new HashSet<>();
        list.forEach(item -> {
            productGoodsIds.add(item.getGoodsId());
        });
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<Goods>()
                .notIn(productGoodsIds.size() > 0, "id", productGoodsIds)
                .eq("state", Goods.STATE_UP);
        List<Goods> goods = goodsService.list(goodsQueryWrapper);
        ArrayList<Map<String, Object>> options = new ArrayList<>();
        goods.forEach(item->{
            Map<String, Object> map = new HashMap<>();
            map.put("id",item.getId());
            map.put("name",item.getName());
            options.add(map);
        });

        return options;
    }

    @Override
    public void savePointGoods(PointProducts pointProducts,String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        QueryWrapper<PointProducts> wrapper = new QueryWrapper<PointProducts>().eq("goods_id", pointProducts.getGoodsId());
        PointProducts one = super.getOne(wrapper);
        if (one!=null){
            throw new BusinessException("该商品已经是积分商品");
        }
        pointProducts.setUpdateby(employee.getNickName());
        pointProducts.setUpdateTime(new Date());
        pointProducts.setUpdateId(employee.getId());
        pointProducts.setState(PointProducts.STATE_NORMAL);
        super.save(pointProducts);
    }

    @Override
    public void updatePointGoods(PointProducts pointProducts, String token) {
        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        pointProducts.setUpdateby(employee.getNickName());
        pointProducts.setUpdateTime(new Date());
        pointProducts.setUpdateId(employee.getId());
        UpdateWrapper<PointProducts> updateWrapper = new UpdateWrapper<PointProducts>().set("integral", pointProducts.getIntegral()).eq("goods_id", pointProducts.getGoodsId());
        super.update(updateWrapper);
    }

    @Override
    public void del(Long id) {
        UpdateWrapper<PointProducts> updateWrapper = new UpdateWrapper<PointProducts>()
                .set("state", PointProducts.STATE_DEL)
                .eq("goods_id", id);
        super.update(updateWrapper);
    }
}
