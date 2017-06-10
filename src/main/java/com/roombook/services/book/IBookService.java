package com.roombook.services.book;

import com.roombook.entity.BookInfo;
import com.roombook.services.common.ICommService;
import com.roombook.vo.RoomInfoWithTime;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface IBookService extends ICommService {

    public void getRemainTime(List<RoomInfoWithTime> roomInfoWithTimeList, Date date, long bookInfoId, String submitType) throws Exception;

    public int submitBookInfo(String id, Date date, String roomId, Time startTime, Time endTime, long bookInfoId, String submitType) throws Exception;

    public BookInfo loadBookInfo(long id) throws Exception;

}
