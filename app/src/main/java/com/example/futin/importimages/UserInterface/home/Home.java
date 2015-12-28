package com.example.futin.importimages.UserInterface.home;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.Toast;

import com.example.futin.importimages.R;
import com.example.futin.importimages.RestService.RestService;
import com.example.futin.importimages.RestService.listeners.AsyncTaskListener;
import com.example.futin.importimages.RestService.models.Image;
import com.example.futin.importimages.RestService.response.RSGetImagesResponse;
import com.example.futin.importimages.UserInterface.adapters.GridViewAdapter;
import com.example.futin.importimages.UserInterface.controller.ListHolder;

import java.lang.reflect.Method;
import java.util.ArrayList;


public class Home extends Activity implements AsyncTaskListener{

    RestService rs;
    RSGetImagesResponse response;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    int backCounter=0;
    boolean isChecked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView= (GridView) findViewById(R.id.grid_view);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(!isMobileDataEnabled()){
            initAdapter(null);
        }else{
            isChecked=true;
            rs = new RestService(this);
            rs.getImages();
        }
    }

    void initAdapter(ArrayList<Image> images){
        gridViewAdapter = new GridViewAdapter(Home.this, images);
        gridView.setAdapter(gridViewAdapter);
    }
    @Override
    public void returnDataOnPostExecute(Object obj) {
        response= (RSGetImagesResponse) obj;
        ArrayList<Image> images=response.getListOfImages();
        initAdapter(images);
    }
    //check if wifi is on
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        backCounter++;
        if (backCounter == 2){
            ListHolder.getInstance().clear();
            this.finish();
            super.onBackPressed();
        }else{
            makeToast("Press one more time to exit");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backCounter=0;
                }
            },2000);
        }
    }

    /*
        Simple method for showing messages
    */
    public void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    boolean isMobileDataEnabled(){
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean)method.invoke(cm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileDataEnabled;
    }
/*
    void listForConn(){
        TelephonyManager myTelephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener callStateListener = new PhoneStateListener(){
            public void onDataConnectionStateChanged(int state){
                switch(state){
                    case TelephonyManager.DATA_DISCONNECTED:
                        Log.i("State: ", "Offline");
                        if(isChecked) {
                            isChecked=false;
                        }
                        break;
                    case TelephonyManager.DATA_CONNECTED:
                        if(!isChecked){
                            Log.i("State: ", "Online");
                            rs = new RestService(Home.this);
                            rs.getImages();
                            isChecked=true;
                        }
                        break;
                }
            }
        };
        myTelephonyManager.listen(callStateListener,
                PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
    }
    */
}
