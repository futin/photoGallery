package com.example.futin.importimages.UserInterface.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Futin on 12/24/2015.
 */
public class MyAnimation{

    /*
        Animation used for displaying images that is using scaling and transparency as animation.
    */
    public void setAnimationRandom(Context context, ImageView image, int duration, int animation){
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(context, animation);
        myFadeInAnimation.setDuration(new Random().nextInt(duration));
        image.startAnimation(myFadeInAnimation); //Set animation to your ImageView
    }
    public void setAnimation(Context context, ImageView image, int duration, int animation){
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(context, animation);
        myFadeInAnimation.setDuration(duration);
        image.startAnimation(myFadeInAnimation); //Set animation to your ImageView
    }


   }
