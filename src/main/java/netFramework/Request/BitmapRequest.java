package netFramework.Request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.TransitionRes;
import android.util.Log;

import netFramework.Response.Response;

/**
 * Created by luozhenlong on 2017/12/4.
 */

public class BitmapRequest extends Request {
    private static final String TAG = "BitmapRequest";
    /**
     * @param method
     * @param url
     * @param listener
     */
    public BitmapRequest(HttpMethod method, String url, RequestListener listener) {
        super(method, url, listener);
    }

    @Override
    public Bitmap parseResponse(Response response) {
        byte[] rawData = response.getRawData();
        Bitmap bitmap = BitmapFactory.decodeByteArray(rawData, 0, rawData.length);
        return bitmap;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
