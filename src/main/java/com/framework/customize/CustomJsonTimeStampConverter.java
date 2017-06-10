package com.framework.customize;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 解决Date类型返回json格式为自定义格式
 * Created by Administrator on 2016/2/14.
 */
public class CustomJsonTimeStampConverter extends ObjectMapper {
    public CustomJsonTimeStampConverter(){
        super();
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
}