package com.roombook.controller.book;

import com.roombook.controller.base.BaseController;
import com.roombook.cst.Constants;
import com.roombook.json.BaseJson;
import com.roombook.services.book.IBookService;
import com.roombook.vo.RoomInfoWithTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cblin on 2017/5/10.
 */
@Controller
public class BookController extends BaseController {

    @Resource
    private IBookService bookService;

    private BaseJson queryJson = new BaseJson();

    @ResponseBody
    @RequestMapping(value = "/getRoomInfo", method = RequestMethod.GET)
    public BaseJson getRoomInfo(@RequestParam(value = "classId", defaultValue = "0") String classId, @RequestParam("date") Date date,
                                @RequestParam(value = "bookInfoId", defaultValue = "0") long bookInfoId,
                                @RequestParam(value = "submitType", defaultValue = "create") String submitType) throws Exception {
        queryJson = new BaseJson();
        List<RoomInfoWithTime> roomInfoWithTimeList = Constants.ROOM_INFO_LIST;
        if (!classId.equals("0")) {
            roomInfoWithTimeList = Constants.ROOM_INFO_LIST.stream().filter(x -> x.classId.equals(classId)).collect(Collectors.toList());
        }
        bookService.getRemainTime(roomInfoWithTimeList, date, bookInfoId, submitType);
        queryJson.setData(roomInfoWithTimeList);
        queryJson.setRetcode("0000");
        return queryJson;
    }

    @ResponseBody
    @RequestMapping(value = "/submitBookInfo", method = RequestMethod.POST)
    public BaseJson pcSubmitBookInfo(@RequestParam("date") Date date, @RequestParam("roomId") String roomId,
                                     @RequestParam("startTime") Time startTime, @RequestParam("endTime") Time endTime,
                                     @RequestParam(value = "bookInfoId", defaultValue = "0") long bookInfoId,
                                     @RequestParam(value = "submitType", defaultValue = "create") String submitType) throws Exception {
        queryJson = new BaseJson();
        if (startTime.compareTo(endTime) >= 0 || endTime.getTime() - startTime.getTime() < 30 * 60 * 1000 || endTime.getTime() - startTime.getTime() > 14 * 60 * 60 * 1000) {
            queryJson.setRetcode("0001");
            queryJson.setErrorMsg("时间输入有误！");
            return queryJson;
        }
        java.util.Date now = new java.util.Date();
        Date nowDate = Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(now.getTime()));
        Date earlyDate = new Date(nowDate.getTime() + 60 * 60 * 24 * 1000);
        Date lateDate = new Date(nowDate.getTime() + 6 * 60 * 60 * 24 * 1000);
        if (date.before(earlyDate) || date.after(lateDate)) {
            queryJson.setRetcode("0001");
            queryJson.setErrorMsg("日期超出范围！");
            return queryJson;
        }
        if (date.compareTo(earlyDate) == 0) {
            Calendar nowDateTime = Calendar.getInstance();
            nowDateTime.setTime(now);
            Calendar expireDateTime = Calendar.getInstance();
            expireDateTime.set(nowDateTime.get(Calendar.YEAR), nowDateTime.get(Calendar.MONTH), nowDateTime.get(Calendar.DATE), 20, 55, 0);
            if (nowDateTime.after(expireDateTime)) {
                queryJson.setRetcode("0001");
                queryJson.setErrorMsg("明日房间预订截止！");
                return queryJson;
            }
        }

        String id = getUserSession();
        int result = bookService.submitBookInfo(id, date, roomId, startTime, endTime, bookInfoId, submitType);
        if (result == 0) {
            queryJson.setData(result);
            queryJson.setRetcode("0000");
        } else if (result == 1) {
            queryJson.setRetcode("0001");
            queryJson.setErrorMsg("您已提交过！");
        } else {
            queryJson.setRetcode("0001");
            queryJson.setErrorMsg("与现有时间重叠！");
        }
        return queryJson;
    }


}
