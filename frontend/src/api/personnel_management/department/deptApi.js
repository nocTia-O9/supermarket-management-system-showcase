import {ajaxGet, ajaxPost} from "@/assets/js/common";

const apiPrefix = '/personnel_management/dept'

//获取仓库信息
export function listByQo(data) {
    return ajaxGet(apiPrefix + "/list", data)
}
/*保存*/
export function save(data) {
    return ajaxPost(apiPrefix + "/save", data)
}
/*修改*/
export function update(data) {
    return ajaxPost(apiPrefix + "/update", data)
}
/*停用*/
export function deactivate(id) {
    return ajaxPost(apiPrefix + "/deactivate", {id:id})
}
