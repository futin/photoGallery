package com.example.futin.importimages.UserInterface.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.futin.importimages.R;
import com.example.futin.importimages.RestService.listeners.ListChangeListener;
import com.example.futin.importimages.UserInterface.animation.MyAnimation;
import com.example.futin.importimages.UserInterface.controller.ListHolder;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Futin on 12/25/2015.
 */
public class SingleImageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ListChangeListener listener;
    int listSize=0;
    Button btn_00, btn_01,btn_02, btn_10, btn_11, btn_12;
    Map<String, ArrayList<Palette.Swatch>>colorsMap;
    public SingleImageAdapter(Context context) {
        this.context=context;
        listener= (ListChangeListener) context;
        listSize= ListHolder.getInstance().calculateSizeOfGallery();
    }

    @Override
    public int getCount() {
        if(listSize == 0){
            listener.closeViewPager();
        }
        return listSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView myImage;
        Button btnDelete;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.single_image_adapter, container, false);
        myImage= (ImageView) viewLayout.findViewById(R.id.singleImageView);
        ShareButton shareButton= (ShareButton) viewLayout.findViewById(R.id.shareButton);
        btnDelete= (Button) viewLayout.findViewById(R.id.btnDelete);
        String fileName=ListHolder.getInstance().returnFileName(position);
        initButtons(viewLayout, getColorRGB(fileName), fileName);

        String AbsoluteFilePath=ListHolder.getInstance().getFilePath()+'/';

        final String filePath=AbsoluteFilePath+fileName;

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(filePath);
                boolean isDeleted = file.delete();
                if (isDeleted) {
                    makeToast("Image deleted");
                    listSize--;
                    ListHolder.getInstance().removeFileFromList(position);
                    notifyDataSetChanged();
                    ListHolder.getInstance().notifyGrid();
                }
            }
        });

        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        myImage.setImageBitmap(bitmap);

        new MyAnimation().setAnimation(context,myImage,400,R.anim.fade_in);

        container.addView(viewLayout);
        shareButton.setShareContent(sharePhoto(bitmap));
        return viewLayout;
    }
    /*
        Need to override DestroyItem so switching Objects can be possible
    */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



    void makeToast(String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    void initButtons(View view, ArrayList<Integer> colors, String url){
        btn_00= (Button) view.findViewById(R.id.btn_00);
        btn_01= (Button) view.findViewById(R.id.btn_01);
        btn_02= (Button) view.findViewById(R.id.btn_02);
        btn_10= (Button) view.findViewById(R.id.btn_10);
        btn_11= (Button) view.findViewById(R.id.btn_11);
        btn_12= (Button) view.findViewById(R.id.btn_12);

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
        colorsMap=ListHolder.getInstance().getColorsMap();

        if(colorsMap != null && colorsMap.containsKey(fileName)){
            for(Palette.Swatch swatch : colorsMap.get(fileName)){
                listOfColors.add(swatch.getRgb());
            }
        }
        return listOfColors;
    }
    /*
        Method used for sharing single photo on Facebook
    */
    SharePhotoContent sharePhoto(Bitmap bitmap){
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        return content;
    }

}
