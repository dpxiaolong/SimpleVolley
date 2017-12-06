# SimpleVolley

请求框架模仿自volley，参考了网上的其它网络框架，稳定性还未测试；

支持返回String串的请求

支持json请求

支持图片Url请求。

支持大文件下载断点续传。（注意运行时权限处理—存储权限）

内部已实现多线程同时进行网络请求。
流程图如下：
![image](https://github.com/dpxiaolong/SimpleVolley/blob/master/flow.png)

//使用演示，只需要简单五步即可完成。
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView mResultTv;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultTv = (TextView) findViewById(R.id.result_tv);
        // 1、构建请求队列
        mQueue = SimpleVolley.newRequestQueue();
        //2 .构建一个请求
        StringRequest request = new StringRequest(Request.HttpMethod.GET, "https://www.so.com/",
                new Request.RequestListener<String>() {
                    @Override
                    public void onComplete(int stCode, String response, String errMsg) {
                        Log.i(TAG, "response:" + response + " stCode:" + stCode);
                        //4 .请求完成的时候处理请求结果即可
                        mResultTv.setText(response);
                    }
                });
        //3 .将请求加入请求队列
        mQueue.addRequest(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //5.退出的时候停止请求队列
        mQueue.stop();
    }
}

       

