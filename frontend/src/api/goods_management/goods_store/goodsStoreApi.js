import {ajaxGet, ajaxPost} from "@/assets/js/common";

const apiPrefix = '/goods_management/goods_store'

/*列表显示*/
export function queryPageByQo(data) {
    return ajaxPost(apiPrefix + "/queryPageByQo", data)
}

export function queryGoodsStoreById(data) {
    return ajaxGet(apiPrefix + "/queryGoodsStoreById", data)
}

export function updateInventory(data) {
    return ajaxPost(apiPrefix + "/updateInventory", data)
}
