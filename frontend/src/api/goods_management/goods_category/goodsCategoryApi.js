import {ajaxGet, ajaxPost} from "@/assets/js/common";

const apiPrefix = '/goods_management/goods_category'
/*保存*/
export function save(data) {
    return ajaxPost(apiPrefix + "/save", data)
}
/*修改*/
export function update(data) {
    return ajaxPost(apiPrefix + "/update", data)
}
/*停用*/
export function deactivate(sid) {
    return ajaxPost(apiPrefix + "/deactivate", {cid:sid})
}
/*列表显示*/
export function queryPageByQo(data) {
    return ajaxPost(apiPrefix + "/queryPageByQo", data)
}

export function getNormalCategoryAll(){
    return ajaxGet(apiPrefix+"/normalCategoryAll")
}
