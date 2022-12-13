package com.hu.health.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ObjectUtil {
    public static  <T> T MapToObjBySetter(Map<String, Object> map, Class<T> clz) {
        try {
            Object obj = clz.newInstance();
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                Object val = map.get(name);
                if (val == null) {
                    continue;
                }
                // 拼接setter方法
                String setter="set"+name.substring(0,1).toUpperCase()+name.substring(1);
                Method method = clz.getDeclaredMethod(setter, field.getType());

                Class<?> type = field.getType();
                if (type == Long.class || type == long.class) {
                    method.invoke(obj, Long.parseLong((String) val));
                } else if (type == Integer.class || type == int.class) {
                    method.invoke(obj,Integer.valueOf((String) val));
                } else if (type == Double.class || type == double.class) {
                    method.invoke(obj, Double.parseDouble((String) val));
                } else {
                    method.invoke(obj, val);
                }
            }
            return (T) obj;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
