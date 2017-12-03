package netFramework.HttpStack;

import android.os.Build;

/**根据api版本选择HttpClient或者HttpURLConnection
 * 此选择来自android官方的解释
 * Created by luozhenlong.
 */

public class HttpStackFactory {
    private static final String TAG = "HttpStackFactory";

    private static final int GINGERBREAD_SDK_NUM = 9;

    /**
     * 根据SDK版本号来创建不同的Http执行器,即SDK 9之前使用HttpClient,之后则使用HttlUrlConnection,
     * 两者之间的差别请参考官方说明
     *
     *
     * @return
     */
    public static HttpStack createHttpStack() {
        int runtimeSDKApi = Build.VERSION.SDK_INT;
        if (runtimeSDKApi >= GINGERBREAD_SDK_NUM) {
            return new HttpUrlConnStack();
        }

        return new HttpClientStack();
    }

}
