package com.yt.plugin.quartz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * json 工具类
 * @author jetz
 *
 */
public class QzJsonUtils {

	
	/** 
	 * bean转json
	 * @param obj
	 * @return
	 */
	public static String beanToJson(Object obj){
		return JSON.toJSONString(obj);
	}
	
	/**
	 * json转bean
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T>T jsonToBean(String json,Class<T> clazz){
		return JSON.parseObject(json, clazz);
	}
	
	
	/**
	 * bean转json
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		return beanToJson(obj);
	}
	
	/**
	 * json转bean
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T>T fromJson(String json,Class<T> clazz){
		return jsonToBean(json, clazz);
	}
	
	
	/**
	 * json转list
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T>List<T> jsonToList(String json,Class<T> clazz){
		return JSON.parseArray(json, clazz);
	}
	
	/**
     * json转JSONObject
     * @param json
     * @return
     */
    public static JSONObject toJSONObject(String json){
        return JSON.parseObject(json);
    }

    /**
     * json 转 map
     * @param json
     * @return
     */
    public static Map<String,Object> toMap(String json){
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject object = toJSONObject(json);
        Set<Map.Entry<String, Object>> set = object.entrySet();
        for(Map.Entry<String, Object> en :set){
            map.put(en.getKey(),en.getValue());
        }
        return map;
    }

    
    public static void main(String[] args) {
    	JSONObject s = QzJsonUtils.toJSONObject("{\"position\":\"123\",\"orderNo\":\"123\"}");
    	System.out.println(s.getString("position"));
	}
}
