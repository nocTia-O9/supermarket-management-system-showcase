import {ajaxGet, ajaxPost} from "@/assets/js/common";

const apiPrefix = '/inventory_management/store/storage_situation'
export function queryPageByQo(data) {
    return ajaxPost(apiPrefix + "/queryPageByQo", data)
}

export function queryStoreGoodsByStoreId(data) {
    return ajaxPost(apiPrefix + "/queryStoreGoodsByStoreId", data)
}
