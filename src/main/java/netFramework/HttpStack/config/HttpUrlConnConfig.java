package netFramework.HttpStack.config;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by luozhenlong on 2017/11/26.
 */

public class HttpUrlConnConfig extends HttpConfig {
    private static HttpUrlConnConfig sConfig = new HttpUrlConnConfig();

    private SSLSocketFactory mSslSocketFactory = null;
    private HostnameVerifier mHostnameVerifier = null;

    private HttpUrlConnConfig() {
    }

    public static HttpUrlConnConfig getConfig() {
        return sConfig;
    }

    /**
     * 配置https请求的SSLSocketFactory与HostnameVerifier
     *
     * @param sslSocketFactory
     * @param hostnameVerifier
     */
    public void setHttpsConfig(SSLSocketFactory sslSocketFactory,
                               HostnameVerifier hostnameVerifier) {
        mSslSocketFactory = sslSocketFactory;
        mHostnameVerifier = hostnameVerifier;
    }

    public HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return mSslSocketFactory;
    }

}
