package com.zncm.leanote.services;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zncm.leanote.data.Key;
import com.zncm.leanote.data.User;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public final class ServiceFactory {

    static RestAdapter restAdapter;
    static Map<Class, Object> serviceMap = new HashMap<Class, Object>();
    static Context context;

    public static void setContext(Context context) {
        ServiceFactory.context = context;
    }


    private static RestAdapter getRestAdapter() {
        if (restAdapter == null) {
//            Gson gson = new GsonBuilder()
//                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
//                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                    .registerTypeAdapter(User.class, new UserJsonDeserializer())
//                    .create();
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Key.API)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setErrorHandler(new RestServiceErrorHandler())
//                    .setConverter(new GsonConverter(gson))
                    .build();
        }
        return restAdapter;
    }

    public static <T> T getService(Class<T> cls) {

        if (serviceMap.containsKey(cls)) {
            return (T) serviceMap.get(cls);
        } else {
            T service = getRestAdapter().create(cls);
            serviceMap.put(cls, service);
            return service;
        }
    }
}
