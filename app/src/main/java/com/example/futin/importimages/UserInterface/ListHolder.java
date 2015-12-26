package com.example.futin.importimages.UserInterface;

import com.example.futin.importimages.RestService.models.Image;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Futin on 12/25/2015.
 */
public class ListHolder {
    ArrayList<Image> images;
    ArrayList<String>combineImages;
    ArrayList<String>listOfFiles;
    ArrayList<String>webUrls=new ArrayList<>();
    File[] files;

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

    String returnFileName(int position){
        if(images == null || position < listOfFiles.size()){
            return listOfFiles.get(position);
        }else{
            if(webUrls.size()>(position-listOfFiles.size()))
            return webUrls.get(position-listOfFiles.size());
        }
        return null;
    }
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
        if(images == null){
            return listOfFiles.size();
        }else{
            return images.size()>listOfFiles.size() ?
                    images.size() : combineImages.size();
        }
    }}
