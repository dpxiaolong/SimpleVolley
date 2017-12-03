package netFramework.Response;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * Created by luozhenlong on 2017/11/26.
 * 响应内容，此处使用org.apache.http.message的BasicHttpResponse
 *
 */

public class Response extends BasicHttpResponse {

    private static final String TAG = "Response";
    public byte[] rawData = new byte[0];

    public Response(ProtocolVersion ver, int code, String reason) {
        super(ver, code, reason);
    }

    public Response(StatusLine statusLine) {
        super(statusLine);
    }


    public byte[] getRawData() {
        return rawData;
    }

    public int getStatusCode() {
        return getStatusLine().getStatusCode();
    }

    public String getMessage() {
        return getStatusLine().getReasonPhrase();
    }

    @Override
    public void setEntity(HttpEntity entity) {
        Log.i(TAG,"setEntity");
        super.setEntity(entity);
        rawData = entityToBytes(getEntity());
    }

    /** Reads the contents of HttpEntity into a byte[]. */
    private byte[] entityToBytes(HttpEntity entity) {
        try {
            return EntityUtils.toByteArray(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
