package com.shanzhu.market.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shanzhu.market.common.redis.constants.RedisKeys;
import com.shanzhu.market.common.redis.service.RedisTemplateService;
import com.shanzhu.market.entity.domain.GoodsCategory;
import com.shanzhu.market.service.IGoodsCategoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CacheDataJob {

    @Resource
    private RedisTemplateService redisTemplateService;

    @Resource
    private IGoodsCategoryService goodsCategoryService;

    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点执行一次
    public void cache_category(){
        System.out.println("被执行。。。。");
        QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<GoodsCategory>()
                .eq("state", GoodsCategory.STATE_NORMAL);
        List<GoodsCategory> list = goodsCategoryService.list(wrapper);
        if (list==null ||list.size()<=0){
            return;
        }
        String cacheKey = RedisKeys.GOODS_CATEGORY.join();
        for (GoodsCategory goodsCategory : list) {
            redisTemplateService.setCacheMapValue(cacheKey,goodsCategory.getId().toString(),goodsCategory);
        }
    }
}
