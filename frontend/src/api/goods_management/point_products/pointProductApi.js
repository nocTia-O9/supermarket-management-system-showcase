import {ajaxGet, ajaxPost} from "@/assets/js/common";

const apiPrefix = '/goods_management/point_products'

/*列表显示*/
export function queryPageByQo(data) {
    return ajaxPost(apiPrefix + "/queryPageByQo", data)
}

export function del(data) {
    return ajaxGet(apiPrefix + "/del", data)
}

export function queryOptionGoods() {
    return ajaxGet(apiPrefix + "/queryOptionGoods", {})
}

export function savePointGoods(data) {
    return ajaxPost(apiPrefix + "/savePointGoods", data)
}


export function queryPointGoodsById(data) {
    return ajaxGet(apiPrefix + "/queryPointGoodsById", data)
}

export function updatePointGoods(data) {
    return ajaxPost(apiPrefix + "/updatePointGoods", data)
}
