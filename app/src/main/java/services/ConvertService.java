package services;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ConvertService {


    public static HashMap<String, String> toHashMap(Object o){
        HashMap<String, String> map = new HashMap<>();
        Field[] fields = o.getClass().getDeclaredFields();
        for(Field f: fields){
            f.setAccessible(true);
            Object value = null;
            try {
                value = f.get(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(f.getName(), value.toString());
        }
        return map;
    }

    public static String toJson(Object o){
        return new Gson().toJson(o);
    }
}
