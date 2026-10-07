import {ajaxGet, ajaxPost} from "@/assets/js/common";

const apiPrefix = '/personnel_management/employee'

//获取仓库信息
export function pageByQo(data) {
    return ajaxPost(apiPrefix + "/list", data)
}
/*保存*/
export function save(data) {
    return ajaxPost(apiPrefix + "/save", data)
}
/*修改*/
export function update(data) {
    return ajaxPost(apiPrefix + "/update", data)
}
/*离职*/
export function deactivate(id) {
    return ajaxPost(apiPrefix + "/deactivate", {id:id})
}
/*详情*/
export function detail(id) {
    return ajaxGet(apiPrefix + "/detail", {uid:id})
}
/*修改按钮*/
export function editBtnClick(id) {
    return ajaxGet(apiPrefix + "/editbtn", {uid:id})
}
/*重置密码*/
export function resetPwd(data){
    return ajaxPost(apiPrefix+"/resetPwd",data)
}
