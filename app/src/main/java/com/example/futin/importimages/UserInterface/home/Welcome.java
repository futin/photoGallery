package com.example.futin.importimages.UserInterface.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.futin.importimages.R;

public class Welcome extends Activity {
    private final int DELAYED_TIME = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageView image_welcome= (ImageView) findViewById(R.id.image_welcome);
        animateImage(image_welcome,600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Welcome.this, Home.class);
                startActivity(i);
                finish();
            }
        }, DELAYED_TIME);
    }

    void animateImage(ImageView image, int duration){
        AlphaAnimation anim=new AlphaAnimation(0.3f,1f);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(duration);
        image.setAnimation(anim);

    }



}
