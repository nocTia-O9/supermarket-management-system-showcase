import {ajaxGet, ajaxPost} from "@/assets/js/common";

const apiPrefix = '/goods_management/statistic_sale'

/*列表显示*/
export function queryPageByQo(data) {
    return ajaxPost(apiPrefix + "/queryPageByQo", data)
}
