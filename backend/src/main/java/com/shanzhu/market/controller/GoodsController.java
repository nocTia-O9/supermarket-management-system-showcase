package com.shanzhu.market.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanzhu.market.common.sercurity.annotation.NoRequireLogin;
import com.shanzhu.market.common.util.PathUtils;
import com.shanzhu.market.common.web.response.JsonResult;
import com.shanzhu.market.entity.domain.DetailStoreGoods;
import com.shanzhu.market.entity.domain.Goods;
import com.shanzhu.market.entity.query.QueryGoods;
import com.shanzhu.market.entity.vo.GoodsListVo;
import com.shanzhu.market.service.IGoodsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/goods_management/goods")
public class GoodsController {

    @Resource
    private IGoodsService goodsService;

    /*查询信息*/
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryGoods qo) {
        Page<GoodsListVo> page = goodsService.queryPageByQo(qo);
        return JsonResult.success(page);
    }
    /**
     * 上传图片到阿里云oss
     * 返回网络图片地址,uploaded:1:成功 0:失败
     */
    @NoRequireLogin
    @PostMapping("/uploadImg")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile upload) {
        Map<String, Object> map = new HashMap<>();
        if (upload != null && upload.getSize() > 0) {
            String path = "";
            try {
                path = PathUtils.upload(upload);
                map.put("uploaded", 1);  //成功
                map.put("url", path);  //成功
            } catch (Exception e) {
                e.printStackTrace();
                map.put("uploaded", 0);  //失败
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("message", e.getMessage());
                map.put("error", errorMap);
            }
        } else {
            map.put("uploaded", 0);  //失败
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", "上传失败，图片文件异常");
            map.put("error", errorMap);
        }
        return map;
    }
    /*保存*/
    @PostMapping("/save")
    public JsonResult saveGoods(Goods goods, HttpServletRequest request){
        goodsService.saveGoods(goods,(String) request.getHeader("token"));
        return JsonResult.success();
    }

    /*上/下架*/
    @PostMapping("/upOrdown")
    public JsonResult upOrdown(@NotNull(message = "商品编号不能为空") Long gid, String state,HttpServletRequest request){
        goodsService.upOrdown(gid,state,(String) request.getHeader("token"));
        return JsonResult.success();
    }
    @GetMapping("/queryGoodsById")
    public JsonResult queryGoodsById(@NotNull(message = "商品编号不能为空") Long id){
        return JsonResult.success(goodsService.getById(id));
    }

    @PostMapping("/update")
    public JsonResult update(Goods goods, HttpServletRequest request){
        goodsService.updateGoods(goods,(String)request.getHeader("token"));
        return JsonResult.success();
    }

    @GetMapping("/selected_goodsAll")
    public JsonResult selected_goodsAll(){
        List<Map<String,Object>> list=goodsService.selected_goodsAll();
        return JsonResult.success(list);
    }

    @GetMapping("/selected_storeAll")
    public JsonResult selected_storeAll(){
        List<Map<String,Object>> list=goodsService.selected_storeAll();
        return JsonResult.success(list);
    }

    /*退还商品入库*/
    @PostMapping("/returnGoods")
    public JsonResult returnGoods(DetailStoreGoods detailStoreGoods, HttpServletRequest request){
        goodsService.returnGoods(detailStoreGoods,(String) request.getHeader("token"));
        return JsonResult.success();
    }

}
