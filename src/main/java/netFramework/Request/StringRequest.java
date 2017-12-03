package netFramework.Request;

import android.support.annotation.NonNull;

import netFramework.Response.Response;

/**
 * Created by luozhenlong.
 */

public class StringRequest extends Request<String> {
    private static final String TAG = "StringRequest";

    public StringRequest(HttpMethod method, String url, RequestListener<String> listener) {
        super(method, url, listener);
    }

    @Override
    public String parseResponse(Response response) {
        return new String(response.getRawData());
    }


    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
