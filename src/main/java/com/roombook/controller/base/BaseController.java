package com.roombook.controller.base;

import com.framework.exceptions.NotLoginException;
import com.roombook.cst.Constants;
import com.roombook.json.BaseJson;
import com.framework.exceptions.InvaildParamentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by deru on 2017/2/3.
 */
public class BaseController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * ThreadLocal确保高并发下每个请求的request，response都是独立的
     */
    private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<ServletRequest>();
    private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<ServletResponse>();

    /**
	 * 线程安全初始化reque，respose对象
	 *
     * @param request
	 * @param response
	 */
    @ModelAttribute
    protected void initReqAndRep(HttpServletRequest request, HttpServletResponse response) {
        currentRequest.set(request);
        currentResponse.set(response);
    }

    /**
     * 线程安全
     *
     * @return
     */
    protected HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) currentRequest.get();
    }

    /**
     * 线程安全
     *
     * @return
     */
    protected HttpServletResponse getHttpResponse() {
        return (HttpServletResponse) currentResponse.get();
    }

    protected void userLogout(){
        getHttpRequest().getSession().invalidate();
    }

    protected void setUserSession(Object value) {
        getHttpRequest().getSession().setAttribute(Constants.USER_Id, value);
    }

    protected String getUserSession() {
        HttpSession session = getHttpRequest().getSession();
        if (session.getAttribute(Constants.USER_Id) == null)
            throw new NotLoginException();
        return session.getAttribute(Constants.USER_Id).toString();
    }

    /**
     * 保存session数据
     * @param key
     * @param value
     */
    protected void setSessionAttribute(String key,Object value) {
        getHttpRequest().getSession().setAttribute(key, value);
    }

    /**
     * 获取session数据
     * @param key
     */
    protected Object getSessionAttribute(String key) {
        return getHttpRequest().getSession().getAttribute(key);
    }


    /**
     *
     * @param queryJson
     * @param result 0统一表示正确，其余表示错误
     * @param errmsg 假如错误希望返回的错误信息
     * @return
     */
    protected BaseJson processServiceResult(BaseJson queryJson, int result, String errmsg) {
        if (result == 0) {
            queryJson.setRetcode("0000");
            queryJson.setErrorMsg(null);
        } else {
            queryJson.setRetcode("0001");
            queryJson.setErrorMsg(errmsg);
        }
        return queryJson;
    }

    /**
     *
     * @param queryJson
     * @param result 为null表示错误，否则会将result设置到BaseJson的obj中
     * @param errmsg 假如错误希望返回的错误信息
     * @return
     */
    protected BaseJson processServiceResult(BaseJson queryJson, Object result, String errmsg) {
        if (result != null) {
            queryJson.setData(result);
            queryJson.setRetcode("0000");
        } else {
            queryJson.setRetcode("0001");
            queryJson.setErrorMsg(errmsg);
        }
        return queryJson;
    }


    protected int parseInt(String s){
        try{
            return Integer.parseInt(s);
        }catch (NumberFormatException e){
            throw new InvaildParamentException();
        }
    }

    protected long parseLong(String s){
        try{
            return Long.parseLong(s);
        }catch (NumberFormatException e){
            throw new InvaildParamentException();
        }
    }
}
