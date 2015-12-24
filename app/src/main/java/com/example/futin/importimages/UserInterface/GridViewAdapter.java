package com.example.futin.importimages.UserInterface;

import android.content.Context;
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

/**
 * Created by Futin on 12/22/2015.
 */
public class GridViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Image> images;
    ImageLoader imageLoader;
    Image singleImage;
    FileCache fc;
    File file;
    int counter=0;
    public GridViewAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
        fc=new FileCache(context);
        imageLoader=new ImageLoader(context);

    }

    public GridViewAdapter(Context context, File file){
        this.context=context;
        this.file=file;
        imageLoader=new ImageLoader(context);
        fc=new FileCache(context);

    }

    @Override
    public int getCount() {
            return images.size();
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
        Log.i("", "view=");

        ImageView myImage;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.single_image, viewGroup, false);
        myImage= (ImageView) itemView.findViewById(R.id.imageView);
                singleImage = images.get(i);
                imageLoader.DisplayImage(singleImage.getUrl(), myImage);
        return itemView;
    }

    public boolean checkFiles(Image image){
        boolean found=false;
        Log.i("", "Check=");

        if(image != null && fc != null) {
            for (File f : fc.getFileHashCode()) {
                if (String.valueOf(image.getUrl().hashCode()).equalsIgnoreCase(f.getName())) {
                    found = true;
                }
            }
        }
        return found;
    }

}
