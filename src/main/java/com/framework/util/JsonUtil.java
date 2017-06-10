package com.framework.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deru on 2017/3/31.
 */
public class JsonUtil {

    static public Map<String, Object> parseJSON2Map(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for(Object k : json.keySet()){
            if(k.equals("credit")) continue;
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if(v instanceof JSONArray){
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                for (JSONObject json2 : (Iterable<JSONObject>) ((JSONArray) v)) {
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    static public <T> T deserialized(String json,Class<T> cla){
        if(json==null||json.isEmpty())
            return null;
        ObjectMapper objectMapper = new ObjectMapper();
        T obj=null;
        try {
            obj = objectMapper.readValue(json, cla);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    static public <T> List<T> jsonListDeserialized(String json,Class<T> cla){
        if(json==null||json.isEmpty())
            return null;
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = getCollectionType(objectMapper, ArrayList.class, cla);
        List<T> obj=null;
        try {
            obj = objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }


    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
