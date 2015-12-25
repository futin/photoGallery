package com.example.futin.importimages.UserInterface;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.futin.importimages.R;
import com.example.futin.importimages.RestService.imageLoader.FileCache;
import com.example.futin.importimages.RestService.imageLoader.ImageLoader;
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

    Map<String, String> imageMap;
    public GridViewAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
        fc=new FileCache(context);
        imageLoader=new ImageLoader(context);
    }


    @Override
    public int getCount() {
            if(images == null){
                return fc.getFileSize();
            }else{
                initImageMap();
                return images.size();
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
        Log.i("","i1="+i+" and file1:"+fc.getFileSize());
        if(i>=fc.getFileSize() && images != null){
            String url="";
            for(Image img : images){
                if(imageMap.containsKey(String.valueOf(img.getUrl().hashCode()))){
                    url=img.getUrl();
                    break;
                }
            }
            imageLoader.DisplayImage(url, myImage);
            imageMap.remove(String.valueOf(url.hashCode()));
        }else{
            loadFromDisc(myImage,i);
        }
        return itemView;
    }

    void loadFromDisc(ImageView image, int i){
        File file=fc.getFiles()[i];
        Bitmap b=imageLoader.getBitmapFromFile(file);
        image.setImageBitmap(b);
        if(images != null) {
            if (imageMap.containsKey(file.getName())) {
                imageMap.remove(file.getName());
            }
        }
    }


    void initImageMap(){
        imageMap=new HashMap<>();
        for(Image img: images){
            if(!imageMap.containsKey(img.getUrl()))
                imageMap.put(String.valueOf(img.getUrl().hashCode()),String.valueOf(img.getUrl().hashCode()));
        }
    }


}
