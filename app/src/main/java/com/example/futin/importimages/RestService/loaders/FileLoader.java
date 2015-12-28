package com.example.futin.importimages.RestService.loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.example.futin.importimages.R;
import com.example.futin.importimages.UserInterface.animation.MyAnimation;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Futin on 12/24/2015.
 */
public class FileLoader {
    Context context;
    ImageLoader imageLoader;
    private Map<ImageView, String> imageViews = Collections
            .synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;
    Handler handler;

    public FileLoader(Context context, ImageLoader imageLoader) {
        this.context=context;
        this.imageLoader=imageLoader;
        executorService= Executors.newFixedThreadPool(5);
        handler=new Handler();
    }
    /*
        Send file and imageView where we are want to display photo to PhotoToLoad class,
        and call our PhotosLoader through executor
    */
    public void queuePhoto(String file, ImageView imageView) {
        imageViews.put(imageView, file);

        PhotoToLoad p = new PhotoToLoad(file, imageView);
        executorService.submit(new PhotosLoader(p));
    }
    /*
     When executor calls this class, it is checking if imageView has been reused, if not extract
     Bitmap from FILE that has been sent before. Then BitmapDisplayer class simply handle
      that process.
    */
    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            try {
                if (imageViewReused(photoToLoad))
                    return;
                Bitmap bmp = getBitmapFromFile(photoToLoad.url);
                if (imageViewReused(photoToLoad)){
                    return;
                }
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
    /*
        Method that is used for reusing imageView-s and saving memory
    */
    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }
    /*
        Used to display bitmap in the UI thread
    */
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null) {
                photoToLoad.imageView.setImageBitmap(bitmap);
                new MyAnimation().setAnimationRandom(context, photoToLoad.imageView, 500, R.anim.fade_in_and_scale);
            }
        }
    }
    /*
        Using file sent from GridViewAdapter class to decode it with imageLoader's method,
        end return that as Bitmap.
    */
    public Bitmap getBitmapFromFile(String file){
        File f = getFile(file);

        Bitmap b = imageLoader.decodeFile(f);
        if (b != null)
            return b;
        return null;
    }

    public File getFile(String url) {
        return new File(imageLoader.fileCache.getDirectory(), url);
    }

}
