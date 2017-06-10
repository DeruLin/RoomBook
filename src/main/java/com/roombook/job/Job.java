package com.roombook.job;

import com.framework.util.HttpClientUtil;
import com.roombook.cst.Constants;
import com.roombook.daos.BaseDAOForThread;
import com.roombook.entity.BookInfo;
import com.roombook.entity.User;
import com.roombook.vo.BookUrlComponent;
import com.roombook.vo.RoomInfoWithTime;
import org.apache.http.client.protocol.HttpClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by deru on 2017/4/6.
 */
@Component("job")
@Lazy(false)
public class Job {

    @Resource
    private BaseDAOForThread baseDAO;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "0 0 1 * * ?")  //每天1点
    public void maintainBookInfo() {
        Date date = new Date((new java.util.Date()).getTime() - 60 * 60 * 24 * 1000);
        baseDAO.findAll(BookInfo.class).stream().filter(x -> x.getDate().compareTo(date) < 0).forEach(x -> {
            log.info("going to delete bookInfo:" + x.getId() + " " + x.getDate());
            baseDAO.delete(x);
        });
    }

    @Scheduled(cron = "10 59 20 * * ?")  //每天8点58分50秒
    public void bookRoom() {
        //获得将要预定的所以预定信息
        Date date = new Date((new java.util.Date()).getTime() + 60 * 60 * 24 * 1000);
        List<BookInfo> bookInfoList = baseDAO.findByProperty("date", date, BookInfo.class);

        //获得构建预定url所需的信息
        List<String> userIdList = bookInfoList.stream().map(BookInfo::getStudentId).distinct().collect(Collectors.toList());
        List<User> userList = baseDAO.findByStringIds("openId", userIdList, User.class);
        List<BookUrlComponent> bookUrlComponentList = bookInfoList.stream().map(x -> {
            RoomInfoWithTime roomInfoWithTime = Constants.ROOM_INFO_LIST.stream().filter(y -> y.id.equals(x.getRoomId())).findFirst().get();
            User user = userList.stream().filter(z -> z.getStudentId().equals(x.getStudentId())).findFirst().get();
            String start = x.getDate().toString() + "+" + x.getStartTime().toString().substring(0, 5);
            String end = x.getDate().toString() + "+" + x.getEndTime().toString().substring(0, 5);
            String startTime = x.getStartTime().toString().substring(0, 5).replace(":", "");
            String endTime = x.getEndTime().toString().substring(0, 5).replace(":", "");
            return new BookUrlComponent(roomInfoWithTime.classId, roomInfoWithTime.devId, roomInfoWithTime.kindId, roomInfoWithTime.labId, start, end, startTime, endTime, user.getStudentId(), user.getStudentPwd(), user.getOpenId(), x.getId());
        }).collect(Collectors.toList());

        Map<String, List<BookInfo>> bookInfoMapByUserId = bookInfoList.stream()
                .filter(x -> x.getStatus() == 0).collect(Collectors.groupingBy(BookInfo::getStudentId));
        Map<String, HttpClientContext> contextMapByUserId = new HashMap<>();
        for (Map.Entry<String, List<BookInfo>> entry : bookInfoMapByUserId.entrySet()) {
            User user = userList.stream().filter(x -> x.getOpenId().equals(entry.getKey())).findFirst().get();
            String url = String.format(Constants.LOGIN_URL, user.getStudentId(), user.getStudentPwd());
            HttpClientContext context = HttpClientUtil.getLoginContext(url);
            for (int i = 0; i < 3 && context == null; i++) {
                context = HttpClientUtil.getLoginContext(url);
            }
            contextMapByUserId.put(entry.getKey(), context);
        }
        List<Thread> threadList = new ArrayList<>();
        for (BookUrlComponent item : bookUrlComponentList) {
            String url = String.format(Constants.ROOM_BOOK_QUERY_URL, item.devId, item.labId, item.kindId, item.start, item.end, item.startTime, item.endTime);
            log.info("book url format result:" + url);
            HttpClientContext context = contextMapByUserId.get(item.userId);
            if (context == null) {
                log.info("fail to load context for user id:" + item.userId);
                continue;
            }
            Runnable task = () -> {
                long startTime = System.currentTimeMillis();
                String response = HttpClientUtil.sendGet(url, context);
                long endTime = System.currentTimeMillis();
                while (!response.contains("操作成功") && endTime - startTime < 60 * 2 * 1000) {
                    log.info(Thread.currentThread().getName() + "running " + item.studentId + "booking");
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        log.info("interrupt error");
                    }
                    response = HttpClientUtil.sendGet(url, context);
                    endTime = System.currentTimeMillis();
                }
                BookInfo updateItem = bookInfoList.stream().filter(x -> x.getId().equals(item.bookInfoId)).findFirst().get();
                if (response.contains("操作成功")) {
                    updateItem.setStatus(1);
                    baseDAO.update(updateItem);
                } else {
                    updateItem.setStatus(2);
                    baseDAO.update(updateItem);
                }
            };
            Thread thread = new Thread(task);
            threadList.add(thread);

        }
        threadList.forEach(Thread::start);
    }


}
