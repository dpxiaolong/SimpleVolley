package netFramework;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

import netFramework.Cache.Cache;
import netFramework.Cache.LruMemCache;
import netFramework.HttpStack.HttpStack;
import netFramework.Request.Request;
import netFramework.Response.Response;

/**
 * 网络请求Executor,继承自Thread,从网络请求队列中循环读取请求并且执行
 * Created by luozhenlong.
 */

final class NetworkExecutor extends Thread {
    private static final String TAG = "NetworkExecutor";

    /**
     * 网络请求队列
     */
    private BlockingQueue<Request<?>> mRequestQueue;
    /**
     * 网络请求栈
     */
    private HttpStack mHttpStack;
    /**
     * 结果分发器,将结果投递到主线程
     */
    private static ResponseDelivery mResponseDelivery = new ResponseDelivery();
    /**
     * 请求缓存
     */
    private static Cache<String, Response> mReqCache = new LruMemCache();
    /**
     * 是否停止
     */
    private boolean isStop = false;

    public NetworkExecutor(BlockingQueue<Request<?>> queue, HttpStack httpStack) {
        Log.i(TAG,"NetworkExecutor enter");
        mRequestQueue = queue;
        mHttpStack = httpStack;
    }
    public static int testRun = 0;

    @Override
    public void run() {
        Log.i(TAG,"run enter");
        try {
            while (!isStop) {
                Log.i(TAG,"Thread Name"+this.getName());
                Log.i(TAG,"mRequestQueue.take()"+testRun++);
                final Request<?> request = mRequestQueue.take();
                if (request.isCanceled()) {
                    Log.i(TAG, "取消执行了");
                    continue;
                }
                Response response = null;
                if (isUseCache(request)) {
                    // 从缓存中取
                    response = mReqCache.get(request.getUrl());
                    Log.i(TAG,"isUseCache(request)");
                } else {
                    // 从网络上获取数据
                    response = mHttpStack.performRequest(request);
                    // 如果该请求需要缓存,那么请求成功则缓存到mResponseCache中,默认是需要的。
                    if (request.shouldCache() && isSuccess(response)) {
                        mReqCache.put(request.getUrl(), response);
                    }
                }
                // 分发请求结果
                mResponseDelivery.deliveryResponse(request, response);
            }
        } catch (InterruptedException e) {
            Log.i(TAG, e.toString());
        }
    }

    private boolean isSuccess(Response response) {
        return response != null && response.getStatusCode() == 200;
    }

    private boolean isUseCache(Request<?> request) {
        return request.shouldCache() && mReqCache.get(request.getUrl()) != null;
    }

    public void quit() {
        isStop = true;
        interrupt();
    }

}
