import {ajaxGet, ajaxJson, ajaxPost} from "@/assets/js/common";

const apiPrefix = '/sale_management/exchange_point_products_records'

export function queryPointProductBymemberId(data) {
    return ajaxGet(apiPrefix + "/queryPointProductBymemberId", data)
}

export function queryMemberByGoodsId(data) {
    return ajaxGet(apiPrefix + "/queryMemberByGoodsId", data)
}
export function queryPointProductByGoodsId(data) {
    return ajaxGet(apiPrefix + "/queryPointProductByGoodsId", data)
}

export function saveExchangePointProductRecords(data) {
    return ajaxPost(apiPrefix + "/saveExchangePointProductRecords", data)
}


export function queryOptionsMemberPhone() {
    return ajaxGet(apiPrefix + "/queryOptionsMemberPhone", {})
}

export function queryPageByQoExchangePointProducts(data) {
    return ajaxPost(apiPrefix + "/queryPageByQoExchangePointProducts", data)
}


export function delExchangePointProducts(data) {
    return ajaxGet(apiPrefix + "/delExchangePointProducts", data)
}


export function queryOptionsPointProducts() {
    return ajaxGet(apiPrefix + "/queryOptionsPointProducts", {})
}
export function queryOptionsMember() {
    return ajaxGet(apiPrefix + "/queryOptionsMember", {})
}
