package com.example.luozhenlong.myapplication;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONObject;

import java.io.File;

import netFramework.Request.BitmapRequest;
import netFramework.Request.FileDownloadRequest;
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

    //请求权限返回码code
    public static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultTv = (TextView) findViewById(R.id.result_tv);
        iv_test = (ImageView) findViewById(R.id.iv_test);
        // 1、构建请求队列
        checkStoragePermission();
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

    public void checkStoragePermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG,"do download");
                    FileDownloadRequest fileDownloadRequest = new FileDownloadRequest("http://14.215.166.171/appdl.hicloud.com/dl/appdl/application/apk/58/5829947dd4914e129692a4f12427e4b0/com.tencent.qqlive.1711281257.apk?mkey=5a265127c7067f84&f=2664&c=0&sign=portal@portal1512470478604&source=portalsite&p=.apk"
                            ,null,null,this);
                    Log.i(TAG,"addRequest fileDownloadRequest");
                    mQueue.addRequest(fileDownloadRequest,this);
                } else {

                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //5.退出的时候停止请求队列
        mQueue.stop();
    }
}
