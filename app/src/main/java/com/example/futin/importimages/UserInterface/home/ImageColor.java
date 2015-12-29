package com.example.futin.importimages.UserInterface.home;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.futin.importimages.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Futin on 12/28/2015.
 */
public class ImageColor implements View.OnClickListener{
    Button btn_00, btn_01,btn_02, btn_10, btn_11, btn_12;
    Map<String, ArrayList<Palette.Swatch>> colorsMap;
    Context context;
    public ImageColor(Button btn_00, Button btn_01, Button btn_02, Button btn_10, Button btn_11,
                      Button btn_12, Map<String, ArrayList<Palette.Swatch>> colorsMap,Context context) {
        this.btn_00 = btn_00;
        this.btn_01 = btn_01;
        this.btn_02 = btn_02;
        this.btn_10 = btn_10;
        this.btn_11 = btn_11;
        this.btn_12 = btn_12;
        this.colorsMap = colorsMap;
        this.context=context;
    }
    public void init(View view, String url){
        initButtons(view,getColorRGB(url),url);
    }
     void initButtons(View view, ArrayList<Integer> colors, String url){
        btn_00= (Button) view.findViewById(R.id.btn_00);
        btn_01= (Button) view.findViewById(R.id.btn_01);
        btn_02= (Button) view.findViewById(R.id.btn_02);
        btn_10= (Button) view.findViewById(R.id.btn_10);
        btn_11= (Button) view.findViewById(R.id.btn_11);
        btn_12= (Button) view.findViewById(R.id.btn_12);
/*
        btn_00.setOnClickListener(this);
        btn_01.setOnClickListener(this);
        btn_02.setOnClickListener(this);
        btn_10.setOnClickListener(this);
        btn_11.setOnClickListener(this);
        btn_12.setOnClickListener(this);
*/
         if(colors.size() > 0 && colorsMap.containsKey(url)) {
            setButtonColors(colors);
        }
    }


    void setButtonColors(ArrayList<Integer> colors){
        for(int i=0;i<colors.size();i++) {
            switch (i) {
                case 0:
                    setDrawableBackground(btn_00,i,colors);
                    break;
                case 1:
                    setDrawableBackground(btn_01,i,colors);

                    break;
                case 2:
                    setDrawableBackground(btn_02,i,colors);

                    break;
                case 3:
                    setDrawableBackground(btn_10,i,colors);

                    break;
                case 4:
                    setDrawableBackground(btn_11,i,colors);
                    break;
                case 5:
                    setDrawableBackground(btn_12,i,colors);
                    break;
                default:
                    break;
            }
        }
    }

    void setDrawableBackground(Button btn,int i, ArrayList<Integer> colors){
        GradientDrawable gd= (GradientDrawable) btn.getBackground();
        gd.setColor(colors.get(i));
        btn.setVisibility(View.VISIBLE);

    }


     ArrayList<Integer> getColorRGB (String fileName){
        ArrayList<Integer> listOfColors=new ArrayList<>();

        if(colorsMap != null && colorsMap.containsKey(fileName)){
            for(Palette.Swatch swatch : colorsMap.get(fileName)){
                listOfColors.add(swatch.getRgb());
            }
        }
        return listOfColors;
    }

    @Override
    public void onClick(View view) {
/*        switch (view.getId()){
            case R.id.btn_00:
                Drawable drawable = btn_00.getBackground();
                for(Map.Entry<String, ArrayList<Palette.Swatch>> entry : colorsMap.entrySet()){
                    for(Palette.Swatch swatch : entry.getValue()){
                        if(swatch.getRgb() == color){
                            listOfSame.add(color);
                        }
                    }
                }
                Log.i("","list of same="+listOfSame.toString());
                break;
            case R.id.btn_01:

                break;
            case R.id.btn_02:

                break;
            case R.id.btn_10:

                break;
            case R.id.btn_11:

                break;
            case R.id.btn_12:

                break;

            default:
                break;
        }
        */
    }

    void makeToast(String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
