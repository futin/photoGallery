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
    File[] files;

    private static ListHolder instance;

    public static ListHolder getInstance(){
        if(instance == null)
            instance=new ListHolder();
        return instance;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public ArrayList<String> getCombineImages() {
        return combineImages;
    }

    public void setCombineImages(ArrayList<String> combineImages) {
        this.combineImages = combineImages;
    }

    public ArrayList<String> getListOfFiles() {
        return listOfFiles;
    }

    public void setListOfFiles(ArrayList<String> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

    public void setAllLists(ArrayList<Image> images, ArrayList<String>combineImages,
                            File[] files){
        this.images=images;
        this.combineImages=combineImages;
        this.files=files;
        convertFilesToList();
    }

    void convertFilesToList(){
        listOfFiles=new ArrayList<>();
        for(File f: files)
            listOfFiles.add(f.getName());
    }

    public void clearLists(){
        images.clear();
        combineImages.clear();
        listOfFiles.clear();
    }

    String returnFileName(int position){
        if(images == null){
            return listOfFiles.get(position);
        }else{
            return images.size()>listOfFiles.size() ?
                    String.valueOf(images.get(position)) : combineImages.get(position);
        }
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
