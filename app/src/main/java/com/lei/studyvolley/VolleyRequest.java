package com.lei.studyvolley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by yanle on 2018/3/12.
 */

public class VolleyRequest {
    public static StringRequest stringRequest;
    public static Context context;

    //自定义get
    public static void RequestGet(Context mContext, String url, String tag,VolleyInterface vif) {
       //获取全局的请求队列
        MyApplication.getHttpQueues().cancelAll(tag);
        stringRequest = new StringRequest(Request.Method.GET, url,vif.loadingListener(), vif.errorListener());
        stringRequest.setTag(tag);
        MyApplication.getHttpQueues().add(stringRequest);
        MyApplication.getHttpQueues().start();


    }

    public static void RequestPost(Context mContext, String url, String tag,
                                   final Map<String, String> params, VolleyInterface vif) {
        MyApplication.getHttpQueues().cancelAll(tag);
        stringRequest = new StringRequest(Request.Method.POST, url, vif.loadingListener(), vif.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);
        MyApplication.getHttpQueues().add(stringRequest);
        MyApplication.getHttpQueues().start();
    }
}
