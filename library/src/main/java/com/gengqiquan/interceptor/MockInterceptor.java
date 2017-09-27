package com.gengqiquan.interceptor;


import com.gengqiquan.Parrot;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gengqiquan on 2017/9/27.
 */

public class MockInterceptor implements Interceptor {
    Parrot parrot;

    public MockInterceptor(Parrot parrot) {
        this.parrot = parrot;
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = parrot.mockResult(request);
        if (response != null) {
            return response;
        }
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
        }

        return response;
    }
}


