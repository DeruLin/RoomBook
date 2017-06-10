package com.roombook.handler;

import com.framework.exceptions.InvaildParamentException;
import com.framework.exceptions.NotLoginException;
import com.roombook.json.BaseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by deru on 2017/1/22.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private BaseJson queryJson = new BaseJson();

    private Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public BaseJson NotLoginExceptionHandler(NotLoginException e){
        queryJson = new BaseJson();
        queryJson.setRetcode("0005");
        queryJson.setErrorMsg("用户尚未登录！");
        log.error(e.getMessage());
        e.printStackTrace();
        return queryJson;
    }

    @ExceptionHandler({NumberFormatException.class,MissingServletRequestParameterException.class, TypeMismatchException.class})
    @ResponseBody
    public BaseJson ParameterExceptionHandler(Exception e){
        queryJson = new BaseJson();
        queryJson.setRetcode("0006");
        queryJson.setErrorMsg("请求参数错误或缺少必须参数！");
        log.error(e.getMessage());
        e.printStackTrace();
        return queryJson;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public BaseJson HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e){
        queryJson = new BaseJson();
        queryJson.setRetcode("0006");
        queryJson.setErrorMsg("请使用正确的请求方法！");
        log.error(e.getMessage());
        e.printStackTrace();
        return queryJson;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseJson ExceptionHandler(Exception e){
        queryJson = new BaseJson();
        queryJson.setRetcode("0003");
        queryJson.setErrorMsg("服务器出现异常！");
        log.error(e.getMessage());
        e.printStackTrace();
        return queryJson;
    }
}
