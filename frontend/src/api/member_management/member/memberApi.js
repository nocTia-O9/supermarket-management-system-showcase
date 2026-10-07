import {ajaxGet, ajaxPost} from "@/assets/js/common";

const apiPrefix = '/member_management/member'

/*列表显示*/
export function queryPageByQo(data) {
    return ajaxPost(apiPrefix + "/queryPageByQo", data)
}
/*删除操作*/
export function delMember(data) {
    return ajaxPost(apiPrefix + "/delMember", data)
}
/*保存*/
export function save(data) {
    return ajaxPost(apiPrefix + "/save", data)
}

//修改
export function update(data) {
    return ajaxPost(apiPrefix + "/update", data)
}
/*查询会员信息*/
export function queryMemberById(data) {
    return ajaxGet(apiPrefix + "/queryMemberById", data)
}
export function queryMemberByPhone(data) {
    return ajaxGet(apiPrefix + "/queryMemberByPhone", data)
}
