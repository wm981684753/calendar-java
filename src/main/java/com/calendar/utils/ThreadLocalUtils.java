package com.calendar.utils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtils {
    private static final ThreadLocal<Map<String,Object>> threadCache = new ThreadLocal<>();

    private static void init(){
        Map<String,Object> map = new HashMap<>();
        threadCache.set(map);
    }
    public static void setThreadCache(String key, Object value){
        if(threadCache.get() == null){
            init();
        }
        threadCache.get().put(key, value);
    }

    public static Object getThreadCache(String key){
        if(threadCache.get() == null){
            init();
        }
        return threadCache.get().get(key);
    }

    public static void removeThreadCache(){
        threadCache.remove();
    }
}

