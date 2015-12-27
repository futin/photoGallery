package com.example.futin.importimages.UserInterface;

import android.view.ViewGroup;

import com.example.futin.importimages.RestService.cache.FileCache;
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
    FileCache fileDir;
    GridViewAdapter grid;
    /*
        Singleton class, designed for list manipulation, end sending data between activities
    */
    private static ListHolder instance;

    public static ListHolder getInstance(){
        if(instance == null)
            instance=new ListHolder();
        return instance;
    }
    public void setGrid(GridViewAdapter grid){
        this.grid=grid;
    }

    public void notifyGrid(){
        grid.setFileSize(fileDir.getFileSize());
        grid.reduceList();
        listOfFiles.clear();
        grid.notifyDataSetChanged();
    }

    public void setAllLists(ArrayList<Image> images, ArrayList<String>combineImages
                            ){
        this.images=images;
        this.combineImages=combineImages;
    }

    public void removeFileFromList(int position, SingleImageAdapter adapter, ViewGroup container){
        if(position<listOfFiles.size()){
            listOfFiles.remove(position);
        }else
            adapter.finishUpdate(container);
    }


    public void setFileDir(FileCache file){
        fileDir=file;
        convertFilesToList();
    }

    void convertFilesToList(){
        listOfFiles=new ArrayList<>();
        if(fileDir.getFileSize()>0) {
            for (File f : fileDir.getFiles())
                listOfFiles.add(f.getName());
        }
    }

    public void addToFiles(String url){
        if(!listOfFiles.contains(url))
            listOfFiles.add(url);
    }
    public ArrayList<String> getListOfFiles(){
        return listOfFiles;
    }

    String returnFileName(int position){

        return listOfFiles.get(position);
    }
    /*
        Method for getting absolute path to our humanityFiles directory
    */
    String getFilePath(){
        return fileDir.getDirectory().getAbsolutePath();
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
    }


}
