package com.roombook.services.book.impl;

import com.framework.util.BookUtil;
import com.roombook.cst.Constants;
import com.roombook.entity.BookInfo;
import com.roombook.entity.RoomInfo;
import com.roombook.services.book.IBookService;
import com.roombook.services.common.impl.CommServiceImpl;
import com.roombook.vo.Duration;
import com.roombook.vo.RoomInfoWithTime;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cblin on 2017/5/11.
 */
@Service(value = "bookService")
public class BookService extends CommServiceImpl implements IBookService {

    @Override
    public void getRemainTime(List<RoomInfoWithTime> roomInfoWithTimeList, Date date, long bookInfoId, String submitType) throws Exception {
        List<String> bookIdList = roomInfoWithTimeList.stream().map(x -> x.id).collect(Collectors.toList());
        List<BookInfo> bookInfoList = baseDAO.findAll(BookInfo.class);

        if (submitType.equals("modify"))
            bookInfoList = baseDAO.findAll(BookInfo.class).stream().filter(x -> x.getId() != bookInfoId).collect(Collectors.toList());

        Map<String, List<BookInfo>> bookInfoMapById = bookInfoList.stream()
                .filter(x -> bookIdList.indexOf(x.getRoomId()) != -1 && x.getDate().equals(date))
                .collect(Collectors.groupingBy(BookInfo::getRoomId));
        for (RoomInfoWithTime roomInfoWithTime : roomInfoWithTimeList) {
            List<BookInfo> bookInfoListForId = bookInfoMapById.get(roomInfoWithTime.id);
            roomInfoWithTime.remainTime = BookUtil.getRemainTimeUI(bookInfoListForId == null ? new ArrayList<>() : bookInfoListForId.stream()
                    .map(x -> new Duration(x.getStartTime(), x.getEndTime()))
                    .collect(Collectors.toList()));
        }


    }

    @Override
    public int submitBookInfo(String id, Date date, String roomId, Time startTime, Time endTime, long bookInfoId, String submitType) throws Exception {
        if (submitType.equals("create") && baseDAO.findByProperty("date", date, BookInfo.class).stream().
                filter(x -> x.getStudentId().equals(id)).
                collect(Collectors.toList()).size() > 0 )
            return 1;
        Map<String, Object> proMap = new HashMap<>();
        proMap.put("date", date);
        proMap.put("roomId", roomId);
        List<BookInfo> bookInfoList = baseDAO.findByProperties(proMap, BookInfo.class);
        if (submitType.equals("modify"))
            bookInfoList = bookInfoList.stream().filter(x -> x.getId() != bookInfoId).collect(Collectors.toList());

        if (!BookUtil.isValidDuration(new Duration(startTime, endTime), bookInfoList.stream()
                .map(x -> new Duration(x.getStartTime(), x.getEndTime()))
                .collect(Collectors.toList())))
            return 2;

        List<Duration> durationList = BookUtil.splitDuration(new Duration(startTime, endTime));
        for (Duration duration : durationList) {
            baseDAO.save(new BookInfo(roomId, id, date, duration.startTime, duration.endTime, 0));
        }
        if (submitType.equals("modify")){
            BookInfo bookInfoToDelete=loadBookInfo(bookInfoId);
            if(bookInfoToDelete!=null)
                baseDAO.delete(bookInfoToDelete);
        }

        return 0;
    }

    @Override
    public BookInfo loadBookInfo(long id) throws Exception {
        return baseDAO.findById(id, BookInfo.class);
    }

}
