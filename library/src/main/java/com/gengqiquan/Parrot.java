package com.gengqiquan;

import android.util.Log;


import com.gengqiquan.annotation.MOCK;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gengqiquan on 2017/9/27.
 */

public class Parrot {
    static Map<String, Method> methodMap = new HashMap<>();

    private Parrot() {

    }

    static Class mService;

    public static Parrot create(Class service) {
        mService = service;
        initMethods(service);
        return new Parrot();
    }

    private static void initMethods(Class service) {
        for (Method method : service.getDeclaredMethods()) {
            MOCK mock = method.getAnnotation(MOCK.class);
            if (mock != null) {
                methodMap.put(mock.value(), method);
            }
        }
        Log.d("Parrot methods:", methodMap.toString());
    }

    private String mockKey(Request request) {
        String url = request.url().toString();
        for (String key : methodMap.keySet()) {
            if (url.contains(key)) {
                return key;
            }
        }

        return null;
    }
    public Response mockResult(Request request) {
        String key = mockKey(request);
        if (key == null) {
            return null;
        }
        MockResult result = getResult(key, request);
        if (result == null) {
            return null;
        }
        return result.getResponse();
    }

    public MockResult getResult(String key, Request request) {
        Method method = methodMap.get(key);
        MockResult result = null;
        try {
            result = (MockResult) method.invoke(mService.newInstance(), request);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return result;
    }
}


