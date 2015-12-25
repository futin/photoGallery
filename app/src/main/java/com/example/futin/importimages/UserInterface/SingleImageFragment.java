package com.example.futin.importimages.UserInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.futin.importimages.R;

/**
 * Created by Futin on 12/25/2015.
 */
public class SingleImageFragment extends Activity {

    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_layout);
        viewPager= (ViewPager) findViewById(R.id.pager);

    }


    @Override
    public void onStart() {
        super.onStart();

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        SingleImageAdapter adapter = new SingleImageAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(position);

    }

}
