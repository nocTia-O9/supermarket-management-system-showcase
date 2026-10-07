package com.shanzhu.market.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.redis.service.RedisTemplateService;
import com.shanzhu.market.entity.domain.*;
import com.shanzhu.market.entity.query.QuerySaleRecords;
import com.shanzhu.market.mapper.SaleRecordsMapper;
import com.shanzhu.market.service.IDetailSaleRecordsService;
import com.shanzhu.market.service.IGoodsService;
import com.shanzhu.market.service.IMemberService;
import com.shanzhu.market.service.ISaleRecordsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SaleRecordsServiceImpl extends ServiceImpl<SaleRecordsMapper, SaleRecords> implements ISaleRecordsService {

    @Resource
    private IGoodsService goodsService;

    @Resource
    private RedisTemplateService redisTemplateService;

    @Resource
    private IDetailSaleRecordsService detailSaleRecordsService;

    @Resource
    private IMemberService memberService;

    @Override
    public List<Map<String, Object>> getOptionSaleRecordsGoods() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>().gt("residue_num", 0L);
        List<Goods> list = goodsService.list(wrapper);
        List<Map<String, Object>> goodsList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Goods goods : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", goods.getId());
                map.put("name", goods.getName());
                map.put("residueNum", goods.getResidueNum());
                goodsList.add(map);
            }
        }
        return goodsList;
    }

    @Override
    public SaleRecords saveSaleRecords(SaleRecords saleRecords, String token) {
        Employee employee = JSON.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
        saleRecords.setEid(employee.getId());
        saleRecords.setSellTime(new Date());
        saleRecords.setSellby(employee.getNickName());
        saleRecords.setState(SaleRecords.STATE_NORMAL);
        for (DetailSaleRecords detailSaleRecord : saleRecords.getDetailSaleRecords()) {
            detailSaleRecord.setSellCn(saleRecords.getCn());
            Goods goods = goodsService.getById(detailSaleRecord.getGoodsId());
            UpdateWrapper<Goods> wrapper = new UpdateWrapper<Goods>()
                    .set("sales_volume", goods.getSalesVolume() != null ? goods.getSalesVolume() + detailSaleRecord.getGoodsNum() : detailSaleRecord.getGoodsNum())
                    .set("residue_num", goods.getResidueNum() - detailSaleRecord.getGoodsNum())
                    .eq("id", goods.getId());
            goodsService.update(wrapper);
        }
        detailSaleRecordsService.saveBatch(saleRecords.getDetailSaleRecords());
        super.save(saleRecords);
        if ("1".equals(saleRecords.getType())) {
            //为会员增加积分 积分规则：积分=总金额*5%取整
            String s = saleRecords.getSellTotalmoney() * 0.05 + "";
            long integral = Long.parseLong(s.split("\\.")[0]);
            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<Member>().eq("phone", saleRecords.getMemberPhone());
            Member member = memberService.getOne(memberQueryWrapper);
            UpdateWrapper<Member> memberUpdateWrapper = new UpdateWrapper<Member>()
                    .set("integral", member.getIntegral() != null ? member.getIntegral() +
                            integral : integral)
                    .eq("phone", saleRecords.getMemberPhone());
            memberService.update(memberUpdateWrapper);
        }
        return saleRecords;
    }

    @Override
    public Page<SaleRecords> queryPageByQoSaleRecords(QuerySaleRecords qo) {
        Page<SaleRecords> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<SaleRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", SaleRecords.STATE_NORMAL);
        queryWrapper.eq(StringUtils.hasText(qo.getType()), "type", qo.getType());
        queryWrapper.likeRight(StringUtils.hasText(qo.getCn()), "cn", qo.getCn());
        queryWrapper.ge(StringUtils.hasText(qo.getStartSellTime()), "sell_time", qo.getStartSellTime());
        queryWrapper.le(StringUtils.hasText(qo.getEndSellTime()), "sell_time", qo.getEndSellTime());
        queryWrapper.eq(StringUtils.hasText(qo.getSellway()), "sellway", qo.getSellway());
        super.page(page, queryWrapper);
        List<SaleRecords> records = page.getRecords();
        if (records != null && records.size() > 0) {
            for (SaleRecords record : records) {
                QueryWrapper<DetailSaleRecords> sellCnWrapper = new QueryWrapper<DetailSaleRecords>().eq("sell_cn", record.getCn());
                List<DetailSaleRecords> list = detailSaleRecordsService.list(sellCnWrapper);
                record.setDetailSaleRecords(list);
            }
        }
        return page;
    }

    @Override
    public void delSaleRecords(String cn) {
        UpdateWrapper<SaleRecords> wrapper = new UpdateWrapper<>();
        wrapper.eq("cn", cn);
        wrapper.set("state", SaleRecords.STATE_DEL);
        super.update(wrapper);
    }

}
