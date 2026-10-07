package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.market.common.sercurity.annotation.HasPermisson;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.DetailStoreGoods;
import com.shanzhu.market.entity.query.QueryDetailStoreGoodsOut;
import com.shanzhu.market.entity.vo.DetailStoreGoodsOutVo;
import com.shanzhu.market.service.IDetailStoreGoodsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/inventory_management/detail_store_goods_out")
public class StoreOutController {

    @Resource
    private IDetailStoreGoodsService detailStoreGoodsService;

    @HasPermisson("inventory_management:detail_store_goods_out:list")
    @PostMapping("/queryPageByQoOut")
    public JsonResult queryPageByQoOut(QueryDetailStoreGoodsOut qo){
        Page<DetailStoreGoodsOutVo> page=detailStoreGoodsService.queryPageByQoOut(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("inventory_management:detail_store_goods_out:save")
    @GetMapping("/initOutOptions")
    public JsonResult<Map<String,Object>> initOutOptions(){
        return JsonResult.success(detailStoreGoodsService.initOutOptions());
    }

    @HasPermisson("inventory_management:detail_store_goods_out:save")
    @GetMapping("/changeOutGoods")
    public JsonResult changeOutGoods(Long gid){
        return JsonResult.success(detailStoreGoodsService.changeOutGoods(gid));
    }

    @HasPermisson("inventory_management:detail_store_goods_out:save")
    @GetMapping("/changeOutStore")
    public JsonResult changeOutStore(Long storeId){
        return JsonResult.success(detailStoreGoodsService.changeOutStore(storeId));
    }

    @HasPermisson("inventory_management:detail_store_goods_out:save")
    @PostMapping("/queryOutGoods")
    public JsonResult queryOutGoods(@RequestParam("goodsId") Long goodsId,
                                    @RequestParam("storeId") Long storeId){
        DetailStoreGoodsOutVo vo=detailStoreGoodsService.queryOutGoods(goodsId,storeId);
        return JsonResult.success(vo);
    }

    @HasPermisson("inventory_management:detail_store_goods_out:save")
    @PostMapping("/save")
    public JsonResult saveOut(DetailStoreGoods detailStoreGoods,HttpServletRequest request){
        detailStoreGoodsService.saveOut(detailStoreGoods,(String) request.getHeader("token"));
        return JsonResult.success();
    }

    @HasPermisson("inventory_management:detail_store_goods_out:delOut")
    @PostMapping("/delOut")
    public JsonResult delOut(@NotEmpty(message = "系统编号不能为空") String cn) {
        detailStoreGoodsService.delIn(cn);
        return JsonResult.success();
    }

}
