package com.roombook.controller.user;

import com.framework.util.CookieUtil;
import com.framework.util.HttpClientUtil;
import com.framework.util.JsonUtil;
import com.roombook.controller.base.BaseController;
import com.roombook.cst.Constants;
import com.roombook.entity.BookInfo;
import com.roombook.json.BaseJson;
import com.roombook.services.book.IBookService;
import com.roombook.services.user.IUserService;
import com.roombook.vo.BookInfoUI;
import com.roombook.vo.RoomInfoWithTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by cblin on 2017/5/11.
 */

@Controller
public class UserController extends BaseController {

    @Resource
    private IUserService userService;

    @Resource
    private IBookService bookService;

    private BaseJson queryJson = new BaseJson();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String pcLoginView() throws Exception {
        HttpSession session = getHttpRequest().getSession();
        String userId = String.valueOf(session.getAttribute(Constants.USER_Id));
        if (userId != null && !userId.equals("null"))
            return "redirect:/center";
        else {
            Cookie cokId = CookieUtil.getCookieByName(getHttpRequest(), "id");
            Cookie cokPwd = CookieUtil.getCookieByName(getHttpRequest(), "pwd");
            if (cokId != null && cokPwd != null && !cokId.getValue().equals("") && !cokPwd.getValue().equals("")) {
                String id = cokId.getValue();
                String pwd = cokPwd.getValue();
                String loginRes = HttpClientUtil.sendGet(String.format(Constants.LOGIN_URL, id, pwd));
                if (loginRes.contains("ok")) {
                    session.setAttribute(Constants.USER_Id, id);
                    return "redirect:/center";
                }
            }
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String pcLogin(@RequestParam("id") String id, @RequestParam("pwd") String pwd,
                          @RequestParam(value = "remember", defaultValue = "false") boolean remember, Model model) throws Exception {
        String loginRes = HttpClientUtil.sendGet(String.format(Constants.LOGIN_URL, id, pwd));
        if (loginRes.contains("ok")) {
            userService.setStudentInfo(id, pwd, JsonUtil.parseJSON2Map(JsonUtil.parseJSON2Map(loginRes).get("data").toString()));
            setUserSession(id);
            if (remember) {
                CookieUtil.addCookie(getHttpResponse(), "id", id, 60 * 60 * 24 * 7);
                CookieUtil.addCookie(getHttpResponse(), "pwd", pwd, 60 * 60 * 24 * 7);
            }
            return "redirect:/center";
        } else {
            model.addAttribute("error", "账号或密码错误");
            return "login";
        }
    }

    @RequestMapping(value = "/center", method = RequestMethod.GET)
    public String center(Model model) throws Exception {
        String id = getUserSession();
        Map<String, List<BookInfoUI>> bookInfoMap = userService.getUserBookInfoForMap(id);
        model.addAttribute("bookInfoMap", bookInfoMap);
        return "center";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() throws Exception {
        userLogout();
        CookieUtil.deleteCookie(getHttpResponse(), "id");
        CookieUtil.deleteCookie(getHttpResponse(), "pwd");
        return "redirect:/login";
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String book(@RequestParam(value = "submitType", defaultValue = "create") String submitType, @RequestParam(value = "bookInfoId", defaultValue = "0") long bookInfoId, Model model) throws Exception {
        BookInfo bookInfo = new BookInfo();
        List<RoomInfoWithTime> roomInfoWithTimeList = Constants.ROOM_INFO_LIST;
        if (submitType.equals("modify")) {
            bookInfo = bookService.loadBookInfo(bookInfoId);
        } else {
            bookInfo.setId(0L);
            bookInfo.setRoomId("0");
            bookInfo.setDate(Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format((new java.util.Date()).getTime() + 60 * 60 * 24 * 1000)));
            bookInfo.setStartTime(Time.valueOf("12:00:00"));
            bookInfo.setEndTime(Time.valueOf("12:00:00"));
        }
        bookService.getRemainTime(roomInfoWithTimeList, bookInfo.getDate(), bookInfoId, submitType);
        model.addAttribute("submitType", submitType);
        model.addAttribute("bookInfo", bookInfo);
        model.addAttribute("roomInfoList", roomInfoWithTimeList);
        return "book";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteUserBookInfo", method = RequestMethod.GET)
    public BaseJson deleteUserBookInfo(@RequestParam("bookInfoId") long bookInfoId) throws Exception {
        queryJson = new BaseJson();
        String id = getUserSession();
        boolean result = userService.deleteUserBookInfo(id, bookInfoId);
        return processServiceResult(queryJson, result, "删除失败");
    }


}
