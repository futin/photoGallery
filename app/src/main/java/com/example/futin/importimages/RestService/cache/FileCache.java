package com.example.futin.importimages.RestService.cache;

/**
 * Created by Futin on 12/22/2015.
 */

import android.content.Context;

import java.io.File;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        // Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "humanityFiles");
        }
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        // String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
    }

    public int getFileSize(){
        return cacheDir.listFiles().length;
    }
    public File[] getFiles() {
        return cacheDir.listFiles();
    }
    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }


}
