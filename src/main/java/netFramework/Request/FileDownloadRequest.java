package netFramework.Request;

import android.content.Context;
import android.net.Uri;

/**
 * Created by luozhenlong on 2017/12/5.
 * 用于处理大文件下载的请求，是直接整合android官方的DownloadManager
 */

public class FileDownloadRequest {

    private String mUri;//下载文件的全路径
    private String mPath;//下载后存储的位置
    private String mFileName;//存储的文件名
    private Context mContext;

    public String getmUri() {
        return mUri;
    }

    public void setmUri(String mUri) {
        this.mUri = mUri;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public Context getmContext() {
        return mContext;
    }

    /**
     * @param uri      下载文件的全路径
     * @param path     下载后存储的位置
     * @param fileName 存储的文件名
     */
    public FileDownloadRequest(String uri, String path, String fileName, Context context) {
        this.mUri = uri;
        this.mPath = path;
        this.mFileName = fileName;
        this.mContext =context;
    }



}
