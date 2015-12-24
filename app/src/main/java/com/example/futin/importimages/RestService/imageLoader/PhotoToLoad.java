package com.example.futin.importimages.RestService.imageLoader;

import android.widget.ImageView;

/**
 * Created by Futin on 12/22/2015.
 */
public class PhotoToLoad {
    public String url;
    public ImageView imageView;

    public PhotoToLoad(String u, ImageView i) {
        url = u;
        imageView = i;
    }

}
