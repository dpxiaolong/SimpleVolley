package com.example.luozhenlong.myapplication;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONObject;

import netFramework.Request.BitmapRequest;
import netFramework.Request.JsonRequest;
import netFramework.Request.Request;

import netFramework.Request.StringRequest;
import netFramework.RequestQueue;
import netFramework.SimpleVolley;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView mResultTv;
    private RequestQueue mQueue;
    private ImageView iv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultTv = (TextView) findViewById(R.id.result_tv);
        iv_test = (ImageView) findViewById(R.id.iv_test);
        // 1、构建请求队列
        mQueue = SimpleVolley.newRequestQueue();

//        BitmapRequest bitmapRequest = new BitmapRequest(Request.HttpMethod.GET,
//                "https://img10.360buyimg.com/imgzone/jfs/t12694/327/1472644169/354901/1fedb87/5a20cd3bNecd9ddfa.jpg", new Request.RequestListener<Bitmap>() {
//            @Override
//            public void onComplete(int stCode, Bitmap response, String errMsg) {
//                Log.i(TAG,"onComplete BITMAP");
//                iv_test.setImageBitmap(response);
//            }
//        });
//        mQueue.addRequest(bitmapRequest);


//        for (int i = 0; i < 300 ; i++) {
//            //2 .构建一个请求
//            final StringRequest request = new StringRequest(Request.HttpMethod.GET, "https://www.so.com/",
//                    new Request.RequestListener<String>() {
//                        @Override
//                        public void onComplete(int stCode, String response, String errMsg) {
//                            Log.i(TAG, "response:" + response + " stCode:" + stCode);
//                            //4 .请求完成的时候处理请求结果即可
//                            mResultTv.setText(response);
//                        }
//                    });
//            //3 .将请求加入请求队列
//            mQueue.addRequest(request);
//        }
            //测试json
//            JsonRequest request1 = new JsonRequest(Request.HttpMethod.GET, "http://www.sojson.com/open/api/weather/json.shtml?city=%E6%B7%B1%E5%9C%B3",
//                    new Request.RequestListener<JSONObject>() {
//                        @Override
//                        public void onComplete(int stCode, JSONObject response, String errMsg) {
//                            Log.i(TAG, "json result" + new String(response.toString()));
//                        }
//                    });
//            mQueue.addRequest(request1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //5.退出的时候停止请求队列
        mQueue.stop();
    }
}
