import {ajaxGet, ajaxPost} from "@/assets/js/common";


const apiPrefix = '/inventory_management/detail_store_goods/notice'

export function queryPageNoticeIn(data) {
    return ajaxPost(apiPrefix+ "/queryPageNoticeIn", data)
}
export function queryPageNoticeOut_shelves(data) {
    return ajaxPost(apiPrefix+ "/queryPageNoticeOut_shelves", data)
}

export function saveOut_shelves(data) {
    return ajaxPost(apiPrefix+ "/saveOut_shelves", data)
}

export function queryPageNoticeOut_untreated(data) {
    return ajaxPost(apiPrefix+ "/queryPageNoticeOut_untreated", data)
}

export function resolveOutUntreatedForm(data) {
    return ajaxPost(apiPrefix+ "/resolveOutUntreatedForm", data)
}
