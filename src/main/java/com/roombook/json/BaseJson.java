package com.roombook.json;

import java.io.Serializable;
/**
 *
 * File name：BaseJson
 * Description：JSON基础结构，包含响应码和响应消息，反馈给前台页面
 */
public class BaseJson implements Serializable,Cloneable {

    private String retcode;//响应代码
    private String errorMsg;//错误消息

    private Object data;
    public BaseJson()
    {
        retcode = "0000";
        data = null;
    }

    public BaseJson(String retcode){
        this.retcode = retcode;
        data = "";
    }

    public String getRetcode() {
        return retcode;
    }
    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

}
