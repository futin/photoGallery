package com.example.futin.importimages.UserInterface;

import com.example.futin.importimages.RestService.models.Image;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Futin on 12/25/2015.
 */
public class ListHolder {
    ArrayList<Image> images;
    //list with both disc and web images
    ArrayList<String>combineImages;
    //list of images from disc
    ArrayList<String>listOfFiles;
    //list of images from web
    ArrayList<String>webUrls=new ArrayList<>();
    File[] files;
    /*
        Singleton class, designed for list manipulation, end sending data between activities
    */
    private static ListHolder instance;

    public static ListHolder getInstance(){
        if(instance == null)
            instance=new ListHolder();
        return instance;
    }

    public void setAllLists(ArrayList<Image> images, ArrayList<String>combineImages
                            ){
        this.images=images;
        this.combineImages=combineImages;
    }

    void convertFilesToList(){
        listOfFiles=new ArrayList<>();
        for(File f: files)
            listOfFiles.add(f.getName());
    }

    public void setFiles(File[] files) {
        this.files = files;
        convertFilesToList();
    }

    public void addToWeb(String url){
        if(!webUrls.contains(url))
            webUrls.add(url);
    }

    public void addToFiles(String url){
        if(!listOfFiles.contains(url))
            listOfFiles.add(url);
    }

    String returnFileName(int position){

        return listOfFiles.get(position);
    }
    /*
        Method for getting absolute path to our humanityFiles directory
    */
    String getFilePath(){
        String path="";
        if(files != null && files.length>0){
            path=files[0].getAbsolutePath();
        }
        return path;
    }
    /*
        Calculation for getCount method
    */
    int calculateSizeOfGallery(){
            return listOfFiles.size();
    }

    void clear(){
        if(images != null && combineImages != null) {
            images.clear();
            combineImages.clear();
        }
        listOfFiles.clear();
        webUrls.clear();
    }

    public Object getCalculatedSizeList(){
        if(images == null){
            return listOfFiles;
        }else{
            return images.size()>listOfFiles.size() ?
                    images : combineImages;
        }

    }

}
