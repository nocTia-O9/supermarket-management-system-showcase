
import { getToken } from "@/assets/js/auth";
import axios from "axios";
import { Message } from "element-ui";
import Cookies from "js-cookie";
import qs from 'qs';

// const prefix_url = "http://localhost:9291"
const prefix_url = "http://127.0.0.1:9291"

/*刷新Cooke的存活时间*/
function refreshCookies() {
    var token=getToken()
    if (token){
        //刷新cookie
        axios({
            url:prefix_url+"/checkedToken",
            method:"GET",
            params:{token:token}
        }).then(res=>{
            res=res.data

            if (res.code==200){
                Cookies.set("token", res.data.token, {expires: 1/48})
                Cookies.set("employee", JSON.stringify(res.data.employee), {expires: 1/48})
            }

        }).catch(()=>{
            popup("系统正忙","error")
        })
    }
    return token;

}

/*post请求*/
export function ajaxPost(url, param) {
    return axios({
        url: prefix_url + url,
        method: "POST",
        headers:{
            'token': refreshCookies() //设置token 其中K名要和后端协调好
        },
        data: qs.stringify(param)
    }).catch(() => {
        popup("系统正忙，请稍后访问....", "error")

    })
}
/*json请求*/
export function ajaxJson(url, param) {
    return axios({
        url: prefix_url + url,
        method: "POST",
        headers:{
            'Content-Type':'application/json',
            'token': refreshCookies() //设置token 其中K名要和后端协调好
        },
        data: param
    }).catch(() => {
        popup("系统正忙，请稍后访问....", "error")

    })
}

/*GET请求*/
export function ajaxGet(url, param) {
    return axios({
        url: prefix_url + url,
        method: "GET",
        headers: {
            'token': refreshCookies() //设置token 其中K名要和后端协调好
        },
        params:param
    }).catch(() => {
        popup("系统正忙，请稍后访问....", "error")

    })
}
/*给用户弹出相应的提示*/
export function popup(msg, type) {
    switch (type) {
        case "warning":
            Message.warning(msg);
            break;
        case "error":
            Message.error(msg);
            break;
        case "info":
            Message.info(msg);
            break;
        default:
            Message.success(msg);
    }

}
