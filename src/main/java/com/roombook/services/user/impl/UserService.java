package com.roombook.services.user.impl;

import com.framework.util.HttpClientUtil;
import com.framework.util.JsonUtil;
import com.roombook.cst.Constants;
import com.roombook.entity.BookInfo;
import com.roombook.entity.User;
import com.roombook.services.common.impl.CommServiceImpl;
import com.roombook.services.user.IUserService;
import com.roombook.vo.BookInfoUI;
import com.roombook.vo.RoomInfoWithTime;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cblin on 2017/5/11.
 */
@Service(value = "userService")
public class UserService extends CommServiceImpl implements IUserService {

    @Override
    public String login(String code) throws Exception {
        String url = String.format(Constants.GET_SESSION_KEY_URL, Constants.AppID, Constants.AppSecret, code);
        log.info("sent request for openid url:" + url);
        String responseStr = HttpClientUtil.sendGet(url);
        log.info("request response：" + responseStr);
        Map responseMap = JsonUtil.parseJSON2Map(responseStr);
        String openId = responseMap.get("openid").toString();
        if (openId != null && baseDAO.findById(openId, User.class) == null) {
            log.info("new user,openid:" + openId);
            User user = new User();
            user.setOpenId(openId);
            baseDAO.save(user);
        }
        return openId;
    }

    @Override
    public User getStudentInfo(String userId) throws Exception {
        User user = baseDAO.findById(userId, User.class);
        if (user.getStudentId() == null || user.getStudentId().length() <= 0) {
            return null;
        } else {
            return user;
        }

    }

    @Override
    public void setStudentInfo(String id, String pwd, Map infoMap) throws Exception {
        setStudentInfo(id, pwd, null, infoMap);
    }

    @Override
    public void setStudentInfo(String id, String pwd, String openId, Map infoMap) throws Exception {
        User user = baseDAO.findById(id, User.class);
        if (user == null) {
            user = new User();
            user.setStudentId(id);
            user.setStudentPwd(pwd);
            user.setOpenId(openId);
            user.setDept(infoMap.get("dept") == null ? "" : infoMap.get("dept").toString());
            user.setName(infoMap.get("name") == null ? "" : infoMap.get("name").toString());
            user.setPhone(infoMap.get("phone") == null ? "" : infoMap.get("phone").toString());
            user.setEmail(infoMap.get("email") == null ? "" : infoMap.get("email").toString());
            baseDAO.save(user);
        } else {
            user.setStudentId(id);
            user.setStudentPwd(pwd);
            user.setDept(infoMap.get("dept") == null ? "" : infoMap.get("dept").toString());
            user.setName(infoMap.get("name") == null ? "" : infoMap.get("name").toString());
            user.setPhone(infoMap.get("phone") == null ? "" : infoMap.get("phone").toString());
            user.setEmail(infoMap.get("email") == null ? "" : infoMap.get("email").toString());
            baseDAO.update(user);
        }
    }

    @Override
    public List<BookInfoUI> getUserBookInfo(String id) throws Exception {
        List<BookInfo> bookInfoList = baseDAO.findByProperty("studentId", id, BookInfo.class);
        bookInfoList.sort(Comparator.comparing(BookInfo::getDate));
        Map<Date, List<BookInfo>> bookInfoMapByDate = bookInfoList.stream().collect(Collectors.groupingBy(BookInfo::getDate));
        List<BookInfo> orderBookInfoList = new ArrayList<>();
        List<Date> sortKeyList = new ArrayList<>(bookInfoMapByDate.keySet());
        sortKeyList.sort(Comparator.comparing(x -> x));

        for (Date key : sortKeyList) {
            bookInfoMapByDate.get(key).sort(Comparator.comparing(BookInfo::getStartTime));
            orderBookInfoList.addAll(bookInfoMapByDate.get(key));
        }
        return orderBookInfoList.stream().map(x -> {
            String status = "等待预定中";
            if (x.getStatus() == 1) status = "预定成功";
            if (x.getStatus() == 2) status = "预定失败";
            RoomInfoWithTime roomInfoWithTime = Constants.ROOM_INFO_LIST.stream().filter(y -> y.id.equals(x.getRoomId())).findFirst().get();
            return new BookInfoUI(x.getId(), roomInfoWithTime.roomName, x.getDate(), x.getStartTime().toString().substring(0, 5), x.getEndTime().toString().substring(0, 5), status, x.getStatus());
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<BookInfoUI>> getUserBookInfoForMap(String id) throws Exception {
        List<BookInfo> bookInfoList = baseDAO.findByProperty("studentId", id, BookInfo.class);
        bookInfoList.sort(Comparator.comparing(BookInfo::getDate));
        Map<Date, List<BookInfo>> bookInfoMapByDate = bookInfoList.stream().collect(Collectors.groupingBy(BookInfo::getDate));
        Map<String, List<BookInfoUI>> result = new TreeMap<>();
        List<Date> sortKeyList = new ArrayList<>(bookInfoMapByDate.keySet());
        sortKeyList.sort(Comparator.comparing(x -> x));

        for (Date key : sortKeyList) {
            bookInfoMapByDate.get(key).sort(Comparator.comparing(BookInfo::getStartTime));
            result.put(key.toString(), bookInfoMapByDate.get(key).stream().map(x -> {
                String status = "等待预定中";
                if (x.getStatus() == 1) status = "预定成功";
                if (x.getStatus() == 2) status = "预定失败";
                RoomInfoWithTime roomInfoWithTime = Constants.ROOM_INFO_LIST.stream().filter(y -> y.id.equals(x.getRoomId())).findFirst().get();
                return new BookInfoUI(x.getId(), roomInfoWithTime.roomName, x.getDate(), x.getStartTime().toString().substring(0, 5), x.getEndTime().toString().substring(0, 5), status, x.getStatus());
            }).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public boolean deleteUserBookInfo(String id, long bookInfoId) throws Exception {
        BookInfo bookInfoToDelete = baseDAO.findById(bookInfoId, BookInfo.class);
        if (bookInfoToDelete.getStudentId().equals(id)) {
            baseDAO.delete(bookInfoToDelete);
            return true;
        } else {
            return false;
        }


    }
}
