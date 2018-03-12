package com.lei.studyvolley;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yanle on 2018/3/12.
 */

public class MyApplication extends Application {
    public static RequestQueue queue;//请求队列
    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());

    }
    //得到全局的请求队列
    public static RequestQueue getHttpQueues() {
        return queue;
    }
}
