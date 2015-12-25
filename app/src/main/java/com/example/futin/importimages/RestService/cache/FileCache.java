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
    /*
        Since files are saved as hashCode on our disc, we need to extract that hashcode to create
        File
    */
    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
    }
    /*
        Simple method for getting length of files in our directory
    */
    public int getFileSize(){
        return cacheDir.listFiles().length;
    }
    /*
        Simple method for getting array of Files
    */
    public File[] getFiles() {
        return cacheDir.listFiles();
    }
    /*
        Delete all files if something goes wrong
    */
    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }


}
