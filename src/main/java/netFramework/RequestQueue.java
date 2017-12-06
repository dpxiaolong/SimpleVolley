package netFramework;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import netFramework.HttpStack.HttpStack;
import netFramework.HttpStack.HttpStackFactory;
import netFramework.Request.FileDownloadRequest;
import netFramework.Request.Request;

/**
 * Created by luozhenlong.
 * 内部封装了一个优先级队列，
 * 在构建队列时会启动几个NetworkExecutor
 * ( 子线程 )来从请求队列中获取请求，并且执行请求。
 * 请求队列会根据请求的优先级进行排序
 */

public class RequestQueue {
    private static final String TAG = "RequestQueue";

    /**
     * 请求队列 [ Thread-safe ]
     */
    private BlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<Request<?>>();
    /**
     * 请求的序列化生成器
     */
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    /**
     * 默认的核心数
     */
    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;
    /**
     * CPU核心数 + 1个分发线程数
     */
    private int mDispatcherNums = DEFAULT_CORE_NUMS;
    /**
     * NetworkExecutor,执行网络请求的线程
     */
    private NetworkExecutor[] mDispatchers = null;
    /**
     * Http请求的真正执行者
     */
    private HttpStack mHttpStack;


    /**
     * @param coreNums 线程核心数
     */
    protected RequestQueue(int coreNums, HttpStack httpStack) {
        mDispatcherNums = coreNums;
        mHttpStack = httpStack != null ? httpStack : HttpStackFactory.createHttpStack();
    }

    /**
     * 启动NetworkExecutor
     */
    private final void startNetworkExecutors() {
        mDispatchers = new NetworkExecutor[mDispatcherNums];
        Log.i(TAG, "mDispatcherNums " + mDispatcherNums);
        for (int i = 0; i < mDispatcherNums; i++) {
            mDispatchers[i] = new NetworkExecutor(mRequestQueue, mHttpStack);
            mDispatchers[i].start();
        }
    }

    public void start() {
        stop();
        startNetworkExecutors();
    }

    /**
     * 停止NetworkExecutor
     */
    public void stop() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (int i = 0; i < mDispatchers.length; i++) {
                mDispatchers[i].quit();
            }
        }
    }

    /**
     * 不能重复添加请求
     *
     * @param request
     */
    public void addRequest(Request<?> request) {
        if (!mRequestQueue.contains(request)) {
            request.setSerialNumber(this.generateSerialNumber());
            mRequestQueue.add(request);
        } else {
            Log.i(TAG, "请求队列中已经含有");
        }
    }

    public void addRequest(final FileDownloadRequest request, final Activity activity) {
        String filepath = null;//文件存储路径
        String fileName = null;//文件存储的名字

        Log.i(TAG, "addRequest start download");
        DownloadManager.Request dlrequest = new DownloadManager.Request(Uri.parse(request.getmUri()));

        if (request.getmPath() == null || request.getmPath().equals("")) {
            filepath = Environment.getRootDirectory().toString();
        } else {
            filepath = request.getmPath();
        }
        String pathtemp = request.getmUri();
        String[] split = pathtemp.split("/");
        String fileNameTemp = split[split.length - 1];
        Log.i(TAG, "fileNameTemp" + fileNameTemp);
        if (request.getmFileName() == null || request.getmFileName().equals("")) {
            fileName = fileNameTemp;
        } else {
            fileName = fileNameTemp;
        }
        //指定下载路径和下载文件名
        dlrequest.setDestinationInExternalPublicDir(filepath, fileName);
        //获取下载管理器
        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(dlrequest);

    }

    public boolean MakeFilePath() {
        String path = Environment.getDownloadCacheDirectory().toString();
        File folder = new File(path);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    public void clear() {
        mRequestQueue.clear();
    }

    public BlockingQueue<Request<?>> getAllRequests() {
        return mRequestQueue;
    }

    /**
     * 为每个请求生成一个系列号
     *
     * @return 序列号
     */
    private int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }
}
