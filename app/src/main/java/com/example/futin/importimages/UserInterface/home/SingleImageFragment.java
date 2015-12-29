package com.example.futin.importimages.UserInterface.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.futin.importimages.R;
import com.example.futin.importimages.RestService.listeners.ListChangeListener;
import com.example.futin.importimages.UserInterface.adapters.SingleImageAdapter;
import com.facebook.FacebookSdk;

/**
 * Created by Futin on 12/25/2015.
 */
public class SingleImageFragment extends Activity implements ListChangeListener {

    private ViewPager viewPager;
    SingleImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_layout);
        viewPager= (ViewPager) findViewById(R.id.pager);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }


    @Override
    public void onStart() {
        super.onStart();

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        adapter = new SingleImageAdapter(this,viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
    @Override
    public void closeViewPager() {
        finish();
    }
}
