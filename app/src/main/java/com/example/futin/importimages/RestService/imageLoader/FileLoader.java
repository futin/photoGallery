package com.example.futin.importimages.RestService.imageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

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

    public void queuePhoto(File file, ImageView imageView) {
        imageViews.put(imageView, String.valueOf(file.getName()));

        PhotoToLoad p = new PhotoToLoad(file, imageView);
        executorService.submit(new PhotosLoader(p));
    }
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
                Bitmap bmp = getBitmapFromFile(photoToLoad.file);
                if (imageViewReused(photoToLoad))
                    return;
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.file.getName()))
            return true;
        return false;
    }

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
                new MyAnimation().setAnimation(context, photoToLoad.imageView, 800);
            }
        }
    }
    public Bitmap getBitmapFromFile(File file){
        Bitmap b = imageLoader.decodeFile(file);
        if (b != null)
            return b;
        else{
            return null;
        }
    }

}
