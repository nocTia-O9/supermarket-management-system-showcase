package com.shanzhu.market.common.web.response;

import com.shanzhu.market.common.constants.HttpStatus;

public class JsonResult<T> {
    public static final int CODE_SUCCESS = HttpStatus.CODE_SUCCESS;
    public static final String MSG_SUCCESS = "操作成功";
    public static final int CODE_NOLOGIN = HttpStatus.CODE_NOLOGIN;
    public static final String MSG_NOLOGIN = "请先登录";
    public static final int CODE_ERROR = HttpStatus.CODE_ERROR;
    public static final String MSG_ERROR = "系统异常，请联系管理员";
    public static final int CODE_ERROR_PARAM = HttpStatus.CODE_ERROR_PARAM;  //参数异常

    private int code;  //区分不同结果, 而不再是true或者false
    private String msg;
    private T data;  //除了操作结果之后, 还行携带数据返回
    public JsonResult(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> JsonResult success(T data){
        return new JsonResult(CODE_SUCCESS, MSG_SUCCESS, data);
    }

    public static JsonResult success(){
        return new JsonResult(CODE_SUCCESS, MSG_SUCCESS, null);
    }

    public static <T> JsonResult error(int code, String msg, T data){
        return new JsonResult(code, msg, data);
    }
    public static <T> JsonResult error(int code, String msg){
        return new JsonResult(code, msg, null);
    }
    public static JsonResult defaultError(){
        return new JsonResult(CODE_ERROR, MSG_ERROR, null);
    }


    public static JsonResult noLogin() {
        return new JsonResult(CODE_NOLOGIN, MSG_NOLOGIN, null);
    }

    public static JsonResult noPermission() {
        return new JsonResult(403, "非法访问", null);
    }



}
