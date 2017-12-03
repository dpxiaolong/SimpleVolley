package netFramework.Request;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import netFramework.Response.Response;

/**
 * Created by luozhenlong.
 * 网络请求实现类，实现Json请求
 */

public class JsonRequest extends Request {

    private static final String TAG = "JsonRequest";
    /**
     * @param method
     * @param url
     * @param listener
     */
    public JsonRequest(HttpMethod method, String url, RequestListener<JSONObject> listener) {
        super(method, url, listener);
    }

    @Override
    public JSONObject parseResponse(Response response) {
        String jsonString = new String(response.getRawData());
        Log.i(TAG,"jsonString result"+jsonString);
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
