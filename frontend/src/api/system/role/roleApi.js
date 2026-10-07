import {ajaxGet, ajaxPost} from "@/assets/js/common";

const roleApiPrefix = '/system/role'

//获取角色全部信息
export function roleList(data) {
    return ajaxPost(roleApiPrefix + "/list", data)
}

//停用角色
export function forbiddenRole(id) {
    return ajaxPost(roleApiPrefix + "/forbiddenRole", {rid: id})
}

//修改角色信息
export function edit_role(data) {
    return ajaxPost(roleApiPrefix + "/edit_role", data)
}

//创建角色
export function save_role(data) {
    return ajaxPost(roleApiPrefix + "/save", data)
}

/*授权按钮*/
export function checkPermissons(rid) {
    return ajaxGet(roleApiPrefix + "/checkPermissons", {rid: rid})
}

/*保存角色-权限关系*/
export function saveRolePermissons(data) {
    return ajaxPost(roleApiPrefix + "/saveRolePermissons", data)
}
export function getAllRole(){
    return ajaxGet(roleApiPrefix +"/all",{})
}
/*保存角色和员工的关系*/
export function saveRoleEmp(data) {
    return ajaxPost(roleApiPrefix + "/saveRoleEmp", data)
}

export function queryRoleIdsByEid(eid) {
    return ajaxGet(roleApiPrefix+"/queryRoleIdsByEid",{eid:eid})

}
