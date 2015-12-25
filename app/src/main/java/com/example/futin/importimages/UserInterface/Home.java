package com.example.futin.importimages.UserInterface;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.GridView;

import com.example.futin.importimages.R;
import com.example.futin.importimages.RestService.RestService;
import com.example.futin.importimages.RestService.listeners.AsyncTaskListener;
import com.example.futin.importimages.RestService.models.Image;
import com.example.futin.importimages.RestService.response.RSGetImagesResponse;

import java.io.File;
import java.util.ArrayList;


public class Home extends Activity implements AsyncTaskListener{

    RestService rs;
    RSGetImagesResponse response;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView= (GridView) findViewById(R.id.grid_view);

        if(!isOnline()){
            gridViewAdapter = new GridViewAdapter(Home.this, null);
            gridView.setAdapter(gridViewAdapter);
        }else{
            rs = new RestService(this);
            rs.getImages();
        }
    }

    @Override
    public void returnDataOnPostExecute(Object obj) {
        response= (RSGetImagesResponse) obj;
        ArrayList<Image> images;
        images=response.getListOfImages();
        gridViewAdapter = new GridViewAdapter(Home.this, images);
        gridView.setAdapter(gridViewAdapter);

    }

    //check if wifi is on
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return netInfo != null && netInfo.isConnected();
    }


}
