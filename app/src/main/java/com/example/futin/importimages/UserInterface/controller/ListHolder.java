package com.example.futin.importimages.UserInterface.controller;

import android.support.v7.graphics.Palette;
import android.util.Log;

import com.example.futin.importimages.RestService.cache.FileCache;
import com.example.futin.importimages.RestService.models.Image;
import com.example.futin.importimages.UserInterface.adapters.GridViewAdapter;
import com.example.futin.importimages.UserInterface.adapters.SingleImageAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Futin on 12/25/2015.
 */
public class ListHolder {
    //list with web images
    ArrayList<Image> images;
    //list with both disc and web images
    ArrayList<String>combineImages;
    //list of images from disc
    ArrayList<String>listOfFiles;
    FileCache fileDir;
    GridViewAdapter grid;
    SingleImageAdapter singleImageAdapter;
    ArrayList<String>listOfSame;

    Map<String, ArrayList<Palette.Swatch>> colorsMap=new HashMap<>();
    /*
        Singleton class, designed for list manipulation, end sending data between activities
    */
    private static ListHolder instance;

    public static ListHolder getInstance(){
        if(instance == null)
            instance=new ListHolder();
        return instance;
    }
    public void setAllLists(ArrayList<Image> images, ArrayList<String>combineImages){
        this.images=images;
        this.combineImages=combineImages;
    }

    public void setGrid(GridViewAdapter grid){
        this.grid=grid;
    }

    public void setSingleImageAdapter(SingleImageAdapter singleImageAdapter) {
        this.singleImageAdapter = singleImageAdapter;
    }

    public void setFileDir(FileCache file){
        fileDir=file;
        convertFilesToList();
    }
    public void setColorsMap(Map<String, ArrayList<Palette.Swatch>> colorsMap){
        this.colorsMap=colorsMap;
    }
    public Map<String, ArrayList<Palette.Swatch>> getColorsMap(){
        return colorsMap;
    }


    public void setListOfSame(ArrayList<String> listOfSame) {
        this.listOfSame = listOfSame;
        grid.notifyForSame(listOfSame);
        singleImageAdapter.notifyForSame();
    }


    public void addToFiles(String url){
        if(!listOfFiles.contains(url))
            listOfFiles.add(url);
    }

    public void notifyGrid(){
        grid.notifyGrid(fileDir, true);
        resetFiles();
        grid.notifyDataSetChanged();
    }
    public void notifyGridForSameColor(){
        grid.notifyForSame(listOfSame);
        grid.notifyDataSetChanged();
    }

    public boolean checkForSameMap(){
        return listOfSame != null;
    }

    void resetFiles(){
        listOfFiles.clear();
        for( File f : fileDir.getFiles()) {
            listOfFiles.add(f.getName());
        }
    }

    public void removeFileFromList(int position){
        if(position<listOfFiles.size()){
            listOfFiles.remove(position);
        }
    }
    public void removeFileFromSameList(int position){
        if(position<listOfSame.size()){
            listOfSame.remove(position);
        }
        Log.i("","sameDel="+listOfSame.size());

    }
    public void removeFileFromMap(String file){
        if(colorsMap.containsKey(file))
            colorsMap.remove(file);
    }

    void convertFilesToList(){
        listOfFiles=new ArrayList<>();
        if(fileDir.getFileSize()>0) {
            for (File f : fileDir.getFiles())
                listOfFiles.add(f.getName());
        }
    }

    public String returnFileName(int position){
           return listOfSame == null ? listOfFiles.get(position) : listOfSame.get(position);
    }

    /*
        Method for getting absolute path to our humanityFiles directory
    */
    public String getFilePath(){
        return fileDir.getDirectory().getAbsolutePath();
    }

    /*
        Calculation for getCount method
    */
    public int calculateSizeOfGallery(){
        return listOfSame == null ? listOfFiles.size() : listOfSame.size();
    }

    public void clear(){

        if(images != null)
            images=null;

        if(combineImages != null)
            combineImages=null;

        if(listOfSame != null)
            listOfSame=null;

        if(listOfFiles != null)
        listOfFiles=null;
    }


}
