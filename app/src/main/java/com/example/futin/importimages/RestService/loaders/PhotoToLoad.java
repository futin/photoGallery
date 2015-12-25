package com.example.futin.importimages.RestService.loaders;

import android.widget.ImageView;

import java.io.File;

/**
 * Created by Futin on 12/22/2015.
 */
public class PhotoToLoad {
    public String url;
    public ImageView imageView;
    public File file;

    public PhotoToLoad(String u, ImageView i) {
        url = u;
        imageView = i;
    }
    public PhotoToLoad(File file, ImageView i) {
        this.file = file;
        imageView = i;
    }

}
