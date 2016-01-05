package com.example.futin.importimages.RestService.loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

import com.example.futin.importimages.UserInterface.controller.ListHolder;

import java.io.File;
import java.util.ArrayList;
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
    final Map<String, ArrayList<Palette.Swatch>> colorsMap= ListHolder.getInstance().getColorsMap();

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
                ListHolder.getInstance().setColorsMap(generatePaletteMap(bmp,
                        photoToLoad.url));

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
               // new MyAnimation().setAnimationRandom(context, photoToLoad.imageView, 250, R.anim.fade_in);
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
    public void destroyThreads(){
        executorService.shutdown();
    }
    public Map<String, ArrayList<Palette.Swatch>> generatePaletteMap(Bitmap image,final String url){
        final ArrayList<Palette.Swatch> listOfSwatches=new ArrayList<>();

        Palette.from(image).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch mutedSwatch=palette.getMutedSwatch();
                Palette.Swatch vibrantSwatch=palette.getVibrantSwatch();
                Palette.Swatch darkVibrantSwatch=palette.getDarkVibrantSwatch();
                Palette.Swatch darkMutedSwatch=palette.getDarkMutedSwatch();
                Palette.Swatch lightVibrantSwatch=palette.getLightVibrantSwatch();
                Palette.Swatch lightMutedSwatch=palette.getLightMutedSwatch();

                if(!colorsMap.containsKey(url)){

                    if(mutedSwatch != null){
                        listOfSwatches.add(mutedSwatch);
                    }
                    if(vibrantSwatch != null){
                        listOfSwatches.add(vibrantSwatch);
                    }
                    if(darkVibrantSwatch != null){
                        listOfSwatches.add(darkVibrantSwatch);
                    }
                    if(darkMutedSwatch != null){
                        listOfSwatches.add(darkMutedSwatch);
                    }
                    if(lightVibrantSwatch != null){
                        listOfSwatches.add(lightVibrantSwatch);
                    }
                    if(lightMutedSwatch != null){
                        listOfSwatches.add(lightMutedSwatch);
                    }

                    if(listOfSwatches.size()>0)
                        colorsMap.put(url, listOfSwatches);
                }
            }
        });

        return colorsMap;
    }
}
