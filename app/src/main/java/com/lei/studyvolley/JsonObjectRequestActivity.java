package com.lei.studyvolley;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yanle on 2018/3/12.
 */

public class JsonObjectRequestActivity extends Activity implements View.OnClickListener{
    private EditText et_phone;
    private Button btn_phone;
    private TextView tv_phone;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.josn_activity);
        init();
    }

    public void init() {
        et_phone = findViewById(R.id.et_json_phone);
        btn_phone = findViewById(R.id.btn_json_phone);
        tv_phone = findViewById(R.id.tv_json_phone);
        btn_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        volly_jsonObjectRequest();
    }

    private void volly_jsonObjectRequest() {
        String phoneNumber = et_phone.getText() + "";
        et_phone.setText("");
        String url = "http://www.tuling123.com/openapi/api?key=c7ed0ac9e1954b8e993146d8fa5e2eab&info=" + phoneNumber;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String text = response.getString("text");
                    tv_phone.setText(text);
                } catch (JSONException e) {
                    e.printStackTrace();
                    tv_phone.setText(response + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_phone.setText(error + "");
            }
        });
        request.setTag("JsonObjectRequest");
        //加入到请求队列
        MyApplication.getHttpQueues().add(request);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyApplication.getHttpQueues().cancelAll("JsonObjectRequest");
    }
}
