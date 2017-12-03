package netFramework;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import netFramework.Request.Request;
import netFramework.Response.Response;

/**
 * Created by luozhenlong.
 */

public class ResponseDelivery implements Executor {
    @Override
    public void execute(@NonNull Runnable command) {
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
                request.deliveryResponse(response);
            }
        };

        execute(respRunnable);
    }

}
