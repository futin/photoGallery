package com.example.futin.importimages.UserInterface.animation;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.example.futin.importimages.UserInterface.controller.ListHolder;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Futin on 1/11/2016.
 */
public class PaletteHelper {
    final Map<String, ArrayList<Palette.Swatch>> colorsMap= ListHolder.getInstance().getColorsMap();

    public Map<String, ArrayList<Palette.Swatch>> generatePaletteMap(Bitmap image,final String url){
        final ArrayList<Palette.Swatch> listOfSwatches=new ArrayList<>();

        Palette.from(image).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch mutedSwatch=palette.getMutedSwatch();
                Palette.Swatch vibrantSwatch=palette.getVibrantSwatch();
                Palette.Swatch darkVibrantSwatch=palette.getDarkVibrantSwatch();
                Palette.Swatch darkMutedSwatch=palette.getDarkMutedSwatch();
                Palette.Swatch lightVibrantSwatch=palette.getLightVibrantSwatch();
                Palette.Swatch lightMutedSwatch=palette.getLightMutedSwatch();

                if(!colorsMap.containsKey(url)){

                    if(mutedSwatch != null){
                        listOfSwatches.add(mutedSwatch);
                    }
                    if(vibrantSwatch != null){
                        listOfSwatches.add(vibrantSwatch);
                    }
                    if(darkVibrantSwatch != null){
                        listOfSwatches.add(darkVibrantSwatch);
                    }
                    if(darkMutedSwatch != null){
                        listOfSwatches.add(darkMutedSwatch);
                    }
                    if(lightVibrantSwatch != null){
                        listOfSwatches.add(lightVibrantSwatch);
                    }
                    if(lightMutedSwatch != null){
                        listOfSwatches.add(lightMutedSwatch);
                    }

                    if(listOfSwatches.size()>0)
                        colorsMap.put(url, listOfSwatches);
                }
            }
        });

        return colorsMap;
    }
}
