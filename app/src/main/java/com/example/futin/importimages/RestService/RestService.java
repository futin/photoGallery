package com.example.futin.importimages.RestService;

import com.example.futin.importimages.RestService.listeners.AsyncTaskListener;
import com.example.futin.importimages.RestService.task.RSGetImagesTask;

/**
 * Created by Futin on 12/22/2015.
 */
public class RestService {

    AsyncTaskListener returnData=null;

    public RestService() {
    }
    public RestService(AsyncTaskListener returnData) {
        this.returnData = returnData;
    }

    public void getImages(){
        new RSGetImagesTask(returnData).execute((Void) null);
    }


}
