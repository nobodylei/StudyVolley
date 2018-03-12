package com.lei.studyvolley;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by yanle on 2018/3/12.
 */

public abstract class VolleyInterface {
    private Context mContext;
    private static Response.Listener<String> listentr;
    private static Response.ErrorListener mErrorListener;
    public VolleyInterface(Context context,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
        this.mContext = context;
        this.listentr = listener;
        this.mErrorListener = errorListener;
    }

    public abstract void onMySuccess(String result);
    public abstract void onMyError(VolleyError error);

    public Response.Listener<String> loadingListener() {
        listentr = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onMySuccess(response);
            }
        };
        return listentr;
    }

    public Response.ErrorListener errorListener() {
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onMyError(error);
            }
        };
        return mErrorListener;
    }
}
