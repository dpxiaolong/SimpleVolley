package netFramework.HttpStack;

import netFramework.Request.Request;
import netFramework.Response.Response;

/**执行网络请求的接口
 * Created by luozhenlong.
 */

public interface HttpStack {

    /**
     * 执行Http请求
     *
     * @param request 待执行的请求
     * @return
     */
    public Response performRequest(Request<?> request);
}
