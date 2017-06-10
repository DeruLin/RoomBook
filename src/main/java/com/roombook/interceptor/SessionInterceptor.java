package com.roombook.interceptor;

import com.framework.configure.Configure;
import com.framework.util.CookieUtil;
import com.framework.util.HttpClientUtil;
import com.framework.util.JsonUtil;
import com.roombook.cst.Constants;
import com.roombook.entity.User;
import com.roombook.json.BaseJson;
import com.roombook.services.user.IUserService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Created by deru on 2017/1/23.
 */
public class SessionInterceptor implements HandlerInterceptor {

    @Resource
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        String userId = String.valueOf(session.getAttribute(Constants.USER_Id));
        if (userId == null || userId.equals("null")) {
            Cookie cokId = CookieUtil.getCookieByName(httpServletRequest, "id");
            Cookie cokPwd = CookieUtil.getCookieByName(httpServletRequest, "pwd");
            if (cokId != null && cokPwd != null && !cokId.getValue().equals("") && !cokPwd.getValue().equals("")) {
                String id = cokId.getValue();
                String pwd = cokPwd.getValue();
                String loginRes = HttpClientUtil.sendGet(String.format(Constants.LOGIN_URL, id, pwd));
                if (loginRes.contains("ok")) {
                    session.setAttribute(Constants.USER_Id, id);
                }
            } else {
                CookieUtil.deleteCookie(httpServletResponse, "id");
                CookieUtil.deleteCookie(httpServletResponse, "pwd");
                httpServletResponse.sendRedirect("/pc/login");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (!isAjaxRequest(httpServletRequest) && httpServletRequest.getMethod().equals("GET")) {
            HttpSession session = httpServletRequest.getSession();
            String userId = String.valueOf(session.getAttribute(Constants.USER_Id));
            if (userId != null && !userId.equals("null")) {
                User user = userService.getStudentInfo(userId);
                modelAndView.getModel().put("user", user);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }
}
