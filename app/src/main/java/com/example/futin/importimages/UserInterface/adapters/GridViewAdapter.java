package com.example.futin.importimages.UserInterface.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.futin.importimages.UserInterface.controller.ListHolder;
import com.example.futin.importimages.UserInterface.home.SingleImageFragment;

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
    ArrayList<String>combineImages;
    ArrayList<String>listOfFiles;
    ImageLoader imageLoader;
    FileCache fc;
    FileLoader fileLoader;
    Map<String, String> imageMap;

    int sizeOfGallery=0;
    int fileSize=0;
    boolean deleteMode=false;

    public GridViewAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
        init();
    }

    void init(){
        fc=new FileCache(context);
        imageLoader=new ImageLoader(context);
        fileLoader=new FileLoader(context,imageLoader);
        ListHolder.getInstance().setFileDir(fc);
        ListHolder.getInstance().setGrid(this);
        fileSize=fc.getFileSize();
        listOfFiles=addToFileList();
        if(images != null) {
            initImageMap();
            calculateDifference();
        }
        sizeOfGallery=calculateSizeOfGallery();
    }

    ArrayList<String> addToFileList(){
        ArrayList<String>listOfFiles=new ArrayList<>();
        for(File f : fc.getFiles()){
            listOfFiles.add(f.getName());
        }
        return listOfFiles;
    }

    public void notifyGrid(FileCache file, boolean mode){
        fileSize=file.getFileSize();
        resetFiles();
        deleteMode=mode;
        reduceList();
    }
    void resetFiles(){
        listOfFiles.clear();
        for( File f : fc.getFiles()) {
            listOfFiles.add(f.getName());
        }
    }

    @Override
    public int getCount() {
       return sizeOfGallery;
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
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.single_image, viewGroup, false);
        myImage = (ImageView) itemView.findViewById(R.id.imageView);
            if (i >= fileSize && images != null) {
                loadFromWeb(myImage, i);
                if(fileSize > 0)
                   fileSize++;
            } else {
                loadFromDisc(myImage, i);
            }
        itemView.setOnClickListener(new OnImageClickListener(i));
        return itemView;
    }
    /*
       Load files from disc, using queuePhoto method in order to place all photos in separate
       threads and display them on main UI.
    */
    void loadFromDisc(ImageView image, int i){
        String file=listOfFiles.get(i);
        fileLoader.queuePhoto(file, image);

        if (images != null) {
            if (imageMap.containsKey(file)) {
                imageMap.remove(file);
            }
        }
    }
    /*
        Load images from web, using DisplayImage method, check MemoryCache for current urls
        and take Bitmaps if any, otherwise put them in queue for loading from internet and,
        then decode and scale them to reduce memory consumption
    */
    void loadFromWeb(ImageView myImage, int position){
        if(fileSize == 0){
            imageLoader.DisplayImage(images.get(position).getUrl(), myImage);
            ListHolder.getInstance().addToFiles(String.valueOf(images.get(position)
                    .getUrl().hashCode()));
            listOfFiles.add(String.valueOf(images.get(position)
                    .getUrl().hashCode()));

        }else{
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
                ListHolder.getInstance().addToFiles(String.valueOf(url.hashCode()));
                listOfFiles.add(String.valueOf(url.hashCode()));
            }
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
    private void calculateDifference(){
        combineImages=new ArrayList<>();
        for(Map.Entry<String, String> e: imageMap.entrySet()){
            combineImages.add(e.getValue());
        }
        if(images != null && fc != null){
            for(File file : fc.getFiles()) {
                if(!imageMap.containsKey(String.valueOf(file.getName()))){
                    combineImages.add((String.valueOf(file.getName())));
                }
            }
        }
    }
    /*
        Calculation for getCount method
    */
    int calculateSizeOfGallery(){
        if(images == null || deleteMode){
            return fc.getFileSize();
        }else{
            return images.size()>fc.getFileSize() ?
                    images.size() : combineImages.size();
        }
    }

    public void reduceList(){
        sizeOfGallery--;
    }

    class OnImageClickListener implements View.OnClickListener {

        int position;

        // constructor
        public OnImageClickListener(int position) {
            this.position = position;
            ListHolder.getInstance().setAllLists(images,combineImages);

        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(context, SingleImageFragment.class);
            i.putExtra("position", position);
            context.startActivity(i);
        }
    }

}
