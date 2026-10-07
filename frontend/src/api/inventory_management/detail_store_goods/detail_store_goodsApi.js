import {ajaxGet, ajaxPost} from "@/assets/js/common";


const apiPrefix_in = '/inventory_management/detail_store_goods_in'
const apiPrefix_out = '/inventory_management/detail_store_goods_out'
/**入库模块**/
/*入库保存*/
export function saveIn(data) {
    return ajaxPost(apiPrefix_in + "/save", data)
}
/*入库列表显示*/
export function queryPageByQoIn(data) {
    return ajaxPost(apiPrefix_in + "/queryPageByQo", data)
}
/*删除入库记录*/
export function delIn(data) {
    return ajaxPost(apiPrefix_in + "/delIn", data)
}
/*删除出库记录*/
export function delOut(data) {
    return ajaxPost(apiPrefix_out + "/delOut", data)
}

export function queryOptionsSuppliers() {
    return ajaxGet(apiPrefix_in + "/queryOptionsSuppliers", {})
}

/**出库模块**/
/*出库列表显示*/
export function queryPageByQoOut(data) {
    return ajaxPost(apiPrefix_out + "/queryPageByQoOut", data)
}
/*初始化选择框*/
export function initOutOptions() {
    return ajaxGet(apiPrefix_out + "/initOutOptions",{})
}
/*出库选择商品导致仓库改变*/
export function changeOutGoods(data) {
    return ajaxGet(apiPrefix_out + "/changeOutGoods",data)
}
/*出库选择仓库导致商品改变*/
export function changeOutStore(data) {
    return ajaxGet(apiPrefix_out + "/changeOutStore",data)
}


export function queryOutGoods(data) {
    return ajaxPost(apiPrefix_out + "/queryOutGoods", data)
}


export function saveOut(data) {
    return ajaxPost(apiPrefix_out + "/save", data)
}
