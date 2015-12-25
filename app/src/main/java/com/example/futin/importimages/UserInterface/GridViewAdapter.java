package com.example.futin.importimages.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.futin.importimages.R;
import com.example.futin.importimages.RestService.cache.FileCache;
import com.example.futin.importimages.RestService.loaders.FileLoader;
import com.example.futin.importimages.RestService.loaders.ImageLoader;
import com.example.futin.importimages.RestService.models.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Futin on 12/22/2015.
 */
public class GridViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Image> images;
    ImageLoader imageLoader;
    FileCache fc;
    FileLoader fileLoader;
    Map<String, String> imageMap;

    int difference=0;

    public GridViewAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
        init();
    }

    void init(){
        fc=new FileCache(context);
        imageLoader=new ImageLoader(context);
        fileLoader=new FileLoader(context,imageLoader);
        if(images != null) {
            initImageMap();
            difference = calculateDifference();
        }
    }

    @Override
    public int getCount() {
            if(images == null){
                return fc.getFileSize();
            }else{
                return images.size()>fc.getFileSize() ?
                        images.size() : images.size()+difference;
            }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView myImage;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.single_image, viewGroup, false);
        myImage= (ImageView) itemView.findViewById(R.id.imageView);

        if(i>=fc.getFileSize() && images != null){
            loadFromWeb(myImage);
        }else{
            loadFromDisc(myImage,i);
        }
        return itemView;
    }
    /*
       Load files from disc, using queuePhoto method in order to place all photos in separate
       threads and display them on main UI.
    */
    void loadFromDisc(ImageView image, int i){
        File file=fc.getFiles()[i];
        fileLoader.queuePhoto(file,image);

        if(images != null) {
            if (imageMap.containsKey(file.getName())) {
                imageMap.remove(file.getName());
            }
        }
    }
    /*
        Load images from web, using DisplayImage method, check MemoryCache for current urls
        and take Bitmaps if any, otherwise put them in queue for loading from internet and,
        then decode and scale them to reduce memory consumption
    */
    void loadFromWeb(ImageView myImage){
        String url="";
        for(Image img : images){
            if(imageMap.containsKey(String.valueOf(img.getUrl().hashCode()))){
                url=img.getUrl();
                break;
            }
        }
        if(!url.equalsIgnoreCase("")) {
            imageLoader.DisplayImage(url, myImage);
            imageMap.remove(String.valueOf(url.hashCode()));
        }
    }
    /*
        Initialisation of map so we can compare elements later
    */
    void initImageMap(){
        imageMap=new HashMap<>();
        for(Image img: images){
            if(!imageMap.containsKey(img.getUrl()))
                imageMap.put(String.valueOf(img.getUrl().hashCode()),String.valueOf(img.getUrl().hashCode()));
        }
    }
    /*
        Calculation of difference between file and database images. If we have more images on disc,
        add that difference in photo gallery, so we can see both images that we do not have from the
        web, and our already downloaded images
    */
    private int calculateDifference(){
        int total=0;
        if(images != null && fc != null){
            for(File file : fc.getFiles()) {
                if(!imageMap.containsKey(String.valueOf(file.getName()))){
                    total++;
                }
            }
        }
        return total;
    }

}
