package com.example.futin.importimages.UserInterface;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;

import com.example.futin.importimages.R;
import com.example.futin.importimages.RestService.RestService;
import com.example.futin.importimages.RestService.listeners.AsyncTaskListener;
import com.example.futin.importimages.RestService.models.Image;
import com.example.futin.importimages.RestService.response.RSGetImagesResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;


public class Home extends Activity implements AsyncTaskListener{

    RestService rs;
    RSGetImagesResponse response;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    Set<String> urls;
    SharedPreferences sp;
    int fileSize=0;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gridView= (GridView) findViewById(R.id.grid_view);

        rs = new RestService(this);
        rs.getImages();

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

    public int humanityFileSize(){
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            file = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "humanityFiles");
            if(file.listFiles() != null)
            return file.listFiles().length;
            else
                return 0;
        }else{
            return 0;
        }
    }

    void waitTime(int time, final Context context){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rs = new RestService((AsyncTaskListener) context);
                rs.getImages();
                finish();
            }
        },time );
    }
}
