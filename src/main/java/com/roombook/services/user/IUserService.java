package com.roombook.services.user;

import com.roombook.entity.BookInfo;
import com.roombook.entity.User;
import com.roombook.services.common.ICommService;
import com.roombook.vo.BookInfoUI;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface IUserService extends ICommService {

    public String login(String code) throws Exception;

    public User getStudentInfo(String userId) throws Exception;

    public void setStudentInfo(String id, String pwd, Map infoMap) throws Exception;

    public void setStudentInfo(String id, String pwd, String openId, Map infoMap) throws Exception;

    public List<BookInfoUI> getUserBookInfo(String id) throws Exception;

    public Map<String, List<BookInfoUI>> getUserBookInfoForMap(String id) throws Exception;

    public boolean deleteUserBookInfo(String id,long bookInfoId) throws Exception;


}
