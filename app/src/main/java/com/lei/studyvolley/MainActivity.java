package com.lei.studyvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_phone;
    private Button btn_phone;
    private TextView tv_phone;
    private Button btn_json;
    private Button btn_post;
    private EditText et_tuling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        et_phone = findViewById(R.id.phone);
        btn_phone = findViewById(R.id.btn_phone);
        tv_phone = findViewById(R.id.tv_phone);
        btn_json = findViewById(R.id.btn_json);
        btn_post = findViewById(R.id.btn_post);
        et_tuling = findViewById(R.id.et_tuling);
        btn_phone.setOnClickListener(this);
        btn_json.setOnClickListener(this);
        btn_post.setOnClickListener(this);
    }

    private void volley_post() {
        final String info = et_tuling.getText() + "";
        et_tuling.setText("");
        String url = "http://www.tuling123.com/openapi/api";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                try {
                    JSONObject json = new JSONObject(response);
                    String text = json.getString("text");
//                    String city = json.getString("city");
//                    String catName = json.getString("catName");
                   // String ua = json.getString("ua");
                    tv_phone.setText(text);
                } catch (JSONException e) {
                    Log.i("tag",e.toString());
                    e.printStackTrace();
                }
                //tv_phone.setText(response + "无数据");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_phone.setText(error + "");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", "c7ed0ac9e1954b8e993146d8fa5e2eab");
                map.put("info", info);
                return map;
            }
        };

        request.setTag("abcPOST");
        MyApplication.getHttpQueues().add(request);
    }

    private void volley_get() {
        String phoneNumber = et_phone.getText() + "";
        String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + phoneNumber;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override//请求成功
            public void onResponse(String response) {
                    try {
                        response = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                        JSONObject json = new JSONObject(response);
                        String province = json.getString("province");
                        String telString = json.getString("telString");
                        String catName = json.getString("catName");
                        // String ua = json.getString("ua");
                        tv_phone.setText(province + " " + telString + "\n" + catName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("tag",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override//请求失败
            public void onErrorResponse(VolleyError error) {
                tv_phone.setText(error + "");
            }
        });
        request.setTag("abcGet");
        //加入到请求队列
        MyApplication.getHttpQueues().add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_phone:
                volley_get();
                break;
            case R.id.btn_json:
                Intent intent = new Intent(MainActivity.this, JsonObjectRequestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_post:
                volley_post();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //通过给定的tag将指定的队列关闭
        MyApplication.getHttpQueues().cancelAll("abcGet");
        MyApplication.getHttpQueues().cancelAll("abcPOST");
    }
}
