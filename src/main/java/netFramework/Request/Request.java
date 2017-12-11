package netFramework.Request;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import netFramework.Response.Response;

/**
 * 网络请求类型抽象类
 * Created by luozhenlong .
 */

public abstract class Request<T> implements Comparable {
    private static final String TAG = "Request";
    /**
     * http请求方法枚举,这里我们只有GET, POST
     *
     */
    public static enum HttpMethod {
        GET("GET"),
        POST("POST"),;

        // http 请求类型
        private String mHttpMethod = "";

        private HttpMethod(String method) {
            mHttpMethod = method;
        }

        @Override
        public String toString() {
            return mHttpMethod;
        }
    }

    /**
     * 该请求是否应该缓存
     *
     * @param shouldCache
     */
    public void setShouldCache(boolean shouldCache) {
        this.mShouldCache = shouldCache;
    }

    public boolean shouldCache() {
        return mShouldCache;
    }


    /**
     * 优先级枚举
     *
     */
    public static enum Priority {
        LOW,
        NORMAL,
        HIGN,
        IMMEDIATE
    }

    //默认的编码格式
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    /**
     * 请求序列号
     */
    protected int mSerialNum = 0;
    /**
     * 优先级默认设置为Normal
     */
    protected Priority mPriority = Priority.NORMAL;
    /**
     * 是否取消该请求
     */
    protected boolean isCancel = false;

    /** 该请求是否应该缓存 */
    private boolean mShouldCache = true;

    /**
     * 请求Listener
     */
    protected RequestListener<T> mRequestListener;
    /**
     * 请求的url
     */
    private String mUrl = "";
    /**
     * 请求的方法
     */
    HttpMethod mHttpMethod = HttpMethod.GET;

    /**
     * Default Content-type
     */
    public final static String HEADER_CONTENT_TYPE = "Content-Type";

    /**
     * 请求的header
     */
    private Map<String, String> mHeaders = new HashMap<String, String>();
    /**
     * 请求参数
     */
    private Map<String, String> mBodyParams = new HashMap<String, String>();

    /**
     * @param method
     * @param url
     * @param listener
     */
    public Request(HttpMethod method, String url, RequestListener<T> listener) {
        mHttpMethod = method;
        mUrl = url;
        this.mRequestListener = listener;
    }

    public boolean isHttps() {
        return mUrl.startsWith("https");
    }

    /**
     * 从原生的网络请求中解析结果,子类覆写
     *
     * @param response
     * @return
     */
    public abstract T parseResponse(Response response);

    /**
     * 处理Response,该方法运行在UI线程.
     *
     * @param response
     */
    public final void deliveryResponse(Response response) {
        // 解析得到请求结果
        T result = parseResponse(response);
        if (mRequestListener != null) {
            int stCode = response != null ? response.getStatusCode() : -1;
            String msg = response != null ? response.getMessage() : "unkown error";
            mRequestListener.onComplete(stCode, result, msg);

        }
    }
    public String getUrl() {
        return mUrl;
    }



    public int getSerialNumber() {
        return mSerialNum;
    }

    public void setSerialNumber(int mSerialNum) {
        this.mSerialNum = mSerialNum;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority mPriority) {
        this.mPriority = mPriority;
    }

    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Map<String, String> getParams() {
        return mBodyParams;
    }


    public void cancel() {
        isCancel = true;
    }

    public boolean isCanceled() {
        return isCancel;
    }

    /**
     * 返回POST或者PUT请求时的Body参数字节数组
     *
     */
    public byte[] getBody() {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, getParamsEncoding());
        }
        return null;
    }

    /**
     * 将参数转换为Url编码的参数串
     */
    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    // 用于对请求的排序处理,根据优先级和加入到队列的序号进行排序

    public int compareTo(Request<T> another) {
        Priority myPriority = this.getPriority();
        Priority anotherPriority = another.getPriority();
        // 如果优先级相等,那么按照添加到队列的序列号顺序来执行
        return myPriority.equals(another) ? this.getSerialNumber() - another.getSerialNumber()
                : myPriority.ordinal() - anotherPriority.ordinal();
    }



    /**
     * 监听请求
     * @param <T>
     */
    public static interface RequestListener<T> {
        /**
         * 请求完成的回调
         *
         * @param response
         */
        public void onComplete(int stCode, T response, String errMsg);
    }

}
