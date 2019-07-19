package com.nandi.lqsbaselibrary.net.callback;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * Date:2018/12/24
 * Time:10:04 AM
 * author:qingsong
 */
public abstract class JsonCallBack<T> extends AbsCallback<T> {
    private Type type;
    private Class<T> clazz;

    public JsonCallBack() {
        this.type = type;
    }

    public JsonCallBack(Class<T> clazz) {
        this.clazz = clazz;
    }
    @Override
    public T convertResponse(okhttp3.Response response) {
        ResponseBody body = response.body();
        if (body == null) return null;
        T data = null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        if (type != null) {
            data = gson.fromJson(jsonReader, type);
        } else if (clazz != null) {
            data = gson.fromJson(jsonReader, clazz);
        } else {
            Type genericSuperclass = getClass().getGenericSuperclass();
            Type actualTypeArgument = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            data = gson.fromJson(jsonReader, actualTypeArgument);
        }
        return data;
    }
}
