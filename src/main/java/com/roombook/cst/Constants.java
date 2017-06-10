package com.roombook.cst;

import com.roombook.vo.RoomInfoWithTime;

import java.util.*;

/**
 * @author jeff_he
 */
public class Constants {

    // HttpSession中存放用户登陆信息的key
    public static final String USER_Id = "userId";

    public static final String MANAGE_USER_Id = "ManageUserId";

    public static final String AppID = "wxbbe54ec756027185";

    public static final String AppSecret = "03f406360f0fc7c8e5b410aed3b45149";

    public static final String GET_SESSION_KEY_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    public static final String GLASS_ROOM_ID = "3674968"; //中北研究室（玻璃门）

    public static final String WOOD_ROOM_ID = "3675132"; //中北研究室（木门）

    public static final String COFFEE_ROOM_ID = "4251118"; //咖啡厅

    public static final String ROOM_INFO_QUERY_URL="http://202.120.82.2:8081/ClientWeb/pro/ajax/device.aspx?classkind=1&class_id=3674968&date=20170509&act=get_rsv_sta";

    public static final String ROOM_BOOK_QUERY_URL="http://202.120.82.2:8081/ClientWeb/pro/ajax/reserve.aspx?dev_id=%s&lab_id=%s&kind_id=%s&type=dev&start=%s&end=%s&start_time=%s&end_time=%s&act=set_resv";

    public static final String LOGIN_URL="http://202.120.82.2:8081/ClientWeb/pro/ajax/login.aspx?act=login&id=%s&pwd=%s";

    public static final String TEST_URL="http://202.120.82.2:8081/ClientWeb/pro/ajax/account.aspx?act=update_contact&phone=18221780000&email=473234000@qq.com";

    public static final List<RoomInfoWithTime> ROOM_INFO_LIST=new ArrayList<RoomInfoWithTime>(){};

    static {
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676604_3674920","3676604","3674969","3674968","3674920","中北校区单人间C411"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676641_3674920","3676641","3674969","3674968","3674920","中北校区单人间C412"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676645_3674920","3676645","3674969","3674968","3674920","中北校区单人间C413"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676656_3674920","3676656","3674969","3674968","3674920","中北校区单人间C414"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676664_3674920","3676664","3674969","3674968","3674920","中北校区单人间C415"));

        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676503_3674920","3676503","3675133","3675132","3674920","中北校区单人间C421"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676511_3674920","3676511","3675133","3675132","3674920","中北校区单人间C422"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676515_3674920","3676515","3675133","3675132","3674920","中北校区单人间C423"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676522_3674920","3676522","3675133","3675132","3674920","中北校区单人间C424"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676538_3674920","3676538","3675133","3675132","3674920","中北校区单人间C425"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676547_3674920","3676547","3675133","3675132","3674920","中北校区单人间C426"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676566_3674920","3676566","3675133","3675132","3674920","中北校区单人间C427"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676574_3674920","3676574","3675133","3675132","3674920","中北校区单人间C428"));
        ROOM_INFO_LIST.add(new RoomInfoWithTime("3676580_3674920","3676580","3675133","3675132","3674920","中北校区单人间C429"));

    }
}
