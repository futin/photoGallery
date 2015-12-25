package com.example.futin.importimages.RestService.data;

/**
 * Created by Futin on 12/22/2015.
 */
public class RSDataSingleton {

    private static RSDataSingleton instance;

    public static RSDataSingleton getInstance() {
        if(instance==null){
            instance=new RSDataSingleton();
        }
        return instance;
    }
    /*
        Singleton class, used for getting server url
    */
    public RSServerUrl getServerUrl(){
        return new RSServerUrl();
    }

}
