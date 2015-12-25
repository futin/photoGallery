package com.example.futin.importimages.UserInterface.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.futin.importimages.R;

import java.util.Random;

/**
 * Created by Futin on 12/24/2015.
 */
public class MyAnimation{

    public void setAnimation(Context context, ImageView image, int duration){
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        myFadeInAnimation.setDuration(new Random().nextInt(duration));
        image.startAnimation(myFadeInAnimation); //Set animation to your ImageView
    }


   }
