package netFramework.Cache;

import android.util.LruCache;

import netFramework.Response.Response;

/**
 * Created by luozhenlong.
 */

public class LruMemCache implements Cache<String, Response> {

    private LruCache<String, Response> mResponseCache;

    public LruMemCache() {
        // 计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // 取八分之一的可用内存作为缓存
        final int cacheSize = maxMemory / 8;
        mResponseCache = new LruCache<String, Response>(cacheSize) {

            @Override
            protected int sizeOf(String key, Response response) {
                return response.rawData.length / 1024;
            }
        };

    }


    @Override
    public Response get(String key) {
        return mResponseCache.get(key);
    }

    @Override
    public void put(String key, Response response) {
        mResponseCache.put(key, response);

    }

    @Override
    public void remove(String key) {
        mResponseCache.remove(key);
    }



}
