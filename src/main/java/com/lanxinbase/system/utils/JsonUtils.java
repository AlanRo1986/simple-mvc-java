package com.lanxinbase.system.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by alan.luo on 2017/7/27.
 */
public class JsonUtils {


    /**
     *
     * @param json string json
     * @param classz object class
     * @param <T> new TypeToken<List<Person>>(){}.getType()
     * @return Object
     */
    public static <T> T jsonToObject(String json, Class<T> classz) {
        Gson gson = new Gson();
        return gson.fromJson(json, classz);
    }


    /**
     * JSON String to Object
     * @param json String
     * @param type Object.class
     * @param <T> Object
     * @return Object
     */
    public static <T> T jsonToObject(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    /**
     * JSON string to Map<String,Object>
     * @param json String
     * @return Map<String, Object>
     */
    public static Map<String, Object> jsonToMap(String json) {
        if(json==null){
            return null;
        }
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, Object> map = g.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
        return map;
    }

    /**
     * JSON string to Map<String,String>
     * @param json String
     * @return Map<String,String>
     */
    public static Map<String, String> jsonToMapString(String json) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, String> map = g.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
        return map;
    }


    /**
     * JSON string to list<Map<>>
     * @param json String
     * @return list<Map<>>
     */
    public static List<Map<String, Object>> jsonToList(String json) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        List<Map<String, Object>> map = g.fromJson(json, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        return map;
    }

    /**
     * JSON string to List Object.
     * @param json String
     * @return List<String>
     */
    public static List<String> jsonToListString(String json) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        List<String> map = g.fromJson(json, new TypeToken<List<String>>() {
        }.getType());
        return map;
    }

    /**
     * object to JSON String
     * @param object object
     * @param isSerializeNull if false will be filtered null values.
     * @return
     */
    public static String objectToJson(Object object, boolean isSerializeNull) {
        if (isSerializeNull) {
            GsonBuilder gb = new GsonBuilder();
            gb.serializeNulls();
            Gson gson = gb.create();
            return gson.toJson(object);
        } else {
            Gson gson = new Gson();
            return gson.toJson(object);
        }
    }

    public static boolean isJson(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }

        String patten = "\\{\\\".*\\}$";
        return str.matches(patten);
    }


}
