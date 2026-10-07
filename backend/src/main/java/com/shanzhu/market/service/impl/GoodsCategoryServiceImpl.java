package com.shanzhu.market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.entity.domain.Goods;
import com.shanzhu.market.entity.domain.GoodsCategory;
import com.shanzhu.market.entity.query.QueryGoodsCategory;
import com.shanzhu.market.mapper.GoodsCategoryMapper;
import com.shanzhu.market.service.IGoodsCategoryService;
import com.shanzhu.market.service.IGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements IGoodsCategoryService {

    @Resource
    private IGoodsService goodsService;

    @Override
    public void updateGoodsCategory(GoodsCategory goodsCategory) {
        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<GoodsCategory>()
                .ne("id",goodsCategory.getId())
                .eq("name", goodsCategory.getName())
                .eq("state", goodsCategory.getState());
        GoodsCategory category = super.getOne(queryWrapper);
        if (GoodsCategory.STATE_BAN.equals(goodsCategory.getState())) {
            //查看是否有上架商品正在使用
            QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().eq("category_id", goodsCategory.getId()).eq("state", Goods.STATE_UP);
            List<Goods> list = goodsService.list(wrapper);
            if (list != null && list.size() > 0) {
                throw new BusinessException("该分类正在被某个上架商品使用，请解除关系后，再操作");
            }
            if (category != null) {
                super.removeById(category);
            }
        } else {
            if (category != null) {
                throw new BusinessException("该分类已经存在");
            }
        }

        super.updateById(goodsCategory);
    }

    @Override
    public void deactivate(Long cid) {
        //查看是否有上架商品正在使用
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>()
                .eq("category_id", cid)
                .eq("state", Goods.STATE_UP);
        List<Goods> list = goodsService.list(wrapper);
        if (list != null && list.size() > 0) {
            throw new BusinessException("该分类正在被某个上架商品使用，请解除关系后，再操作");
        }
        /*查看删除中是否有*/
        GoodsCategory goodsCategory = super.getById(cid);
        QueryWrapper<GoodsCategory> queryWrapper = new QueryWrapper<GoodsCategory>()
                .ne("id",cid)
                .eq("name", goodsCategory.getName())
                .eq("state", GoodsCategory.STATE_BAN);
        GoodsCategory one = super.getOne(queryWrapper);
        if (one != null) {
            super.remove(new QueryWrapper<GoodsCategory>()
                    .eq("name", goodsCategory.getName())
                    .eq("state", GoodsCategory.STATE_BAN));
        }
        super.update(new UpdateWrapper<GoodsCategory>().eq("id", cid).set("state", GoodsCategory.STATE_BAN));
    }

    @Override
    public Page<GoodsCategory> queryPageByQo(QueryGoodsCategory qo) {
        Page<GoodsCategory> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.hasText(qo.getName()), "name", qo.getName());
        wrapper.eq(StringUtils.hasText(qo.getState()), "state", qo.getState());
        return super.page(page, wrapper);
    }

    @Override
    public void saveGoodsCategory(GoodsCategory category) {
        //判断数据库是否保存过这个分类信息
        QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<GoodsCategory>()
                .eq(StringUtils.hasText(category.getName()), "name", category.getName())
                .eq("state", GoodsCategory.STATE_NORMAL);
        GoodsCategory category1 = super.getOne(wrapper);
        if (category1 != null) {
            throw new BusinessException("该分类已被创建");
        }
        category.setState(GoodsCategory.STATE_NORMAL);
        super.save(category);

    }

    @Override
    public List<Map<String, Object>> getNormalCategoryAll() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<GoodsCategory> categories = super.list(new QueryWrapper<GoodsCategory>().eq("state", GoodsCategory.STATE_NORMAL));
        for (GoodsCategory category : categories) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", category.getId());
            map.put("name", category.getName());
            list.add(map);
        }
        return list;
    }
}
