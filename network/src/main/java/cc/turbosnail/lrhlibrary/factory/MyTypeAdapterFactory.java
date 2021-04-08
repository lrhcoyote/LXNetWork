package cc.turbosnail.lrhlibrary.factory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import cc.turbosnail.lrhlibrary.factory.adapter.FloatlNullAdapter;
import cc.turbosnail.lrhlibrary.factory.adapter.IntNullAdapter;
import cc.turbosnail.lrhlibrary.factory.adapter.StringNullAdapter;

public class MyTypeAdapterFactory {
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();

        if (rawType == Float.class || rawType == float.class) {
            return (TypeAdapter<T>) new FloatlNullAdapter();
        }else if (rawType == Integer.class || rawType == int.class){
            return (TypeAdapter<T>) new IntNullAdapter();
        }else if (rawType == String.class){
            return (TypeAdapter<T>) new StringNullAdapter();

        }
        return null;
    }
}
