package com.example.futin.importimages.RestService.data;

/**
 * Created by Futin on 12/22/2015.
 */
public class RSServerUrl {

    /*
        Class used for every path in project
    */
    private final static String API_BASE_URL="http://humanitychallenge.mockable.io";
   // private final static String API_ROOT_URL="/humanity";

    private final static String API_GET_IMAGES_URL="/getImages";
    private final static String API_GET_BCG_IMAGES_URL="/getBcgImages";


    public String getBaseUrl(){
        return API_BASE_URL;
    }
    public String getRootUrl(){
        return getBaseUrl();
    }

    public String getImagesUrl(){return getRootUrl()+API_GET_IMAGES_URL;}


}
