package netFramework;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executor;

import netFramework.Request.Request;
import netFramework.Response.Response;

/**
 * 将结果从子线程发到主线程的控制类
 * Created by luozhenlong.
 */

public class ResponseDelivery implements Executor {
    private static final String TAG = "ResponseDelivery";
    @Override
    public void execute(@NonNull Runnable command) {
        Thread td = Thread.currentThread();
        Log.i(TAG,"execute Thread:"+td.getName()+"id:"+td.getId());
        //这里还是子线程
        //这里主线程的handler执行将结果转发到主线程
        mResponseHandler.post(command);
    }

    /**
     * 主线程的hander
     */
    Handler mResponseHandler = new Handler(Looper.getMainLooper());

    /**
     * 处理请求结果,将其执行在UI线程
     *
     * @param request
     * @param response
     */
    public void deliveryResponse(final Request<?> request, final Response response) {
        Runnable respRunnable = new Runnable() {

            @Override
            public void run() {
                Thread td = Thread.currentThread();
                Log.i(TAG,"Thread:"+td.getName()+"id:"+td.getId());
                request.deliveryResponse(response);
            }
        };

        execute(respRunnable);
    }

}
