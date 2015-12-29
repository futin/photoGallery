package com.example.futin.importimages.UserInterface.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.example.futin.importimages.UserInterface.home.ImageColor;
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
    ImageColor imageColor;
    ViewPager viewPager;
    boolean isColorClicked=false;
    public SingleImageAdapter(Context context, ViewPager viewPager) {
        this.context=context;
        listener= (ListChangeListener) context;
        listSize= ListHolder.getInstance().calculateSizeOfGallery();
        colorsMap=ListHolder.getInstance().getColorsMap();
        isColorClicked= ListHolder.getInstance().checkForSameMap();

        imageColor=new ImageColor(btn_00,btn_01,btn_02,btn_10,btn_11,btn_12,colorsMap, context);
        ListHolder.getInstance().setSingleImageAdapter(this);
        this.viewPager=viewPager;
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
        final String fileName=ListHolder.getInstance().returnFileName(position);
        String fileNameForListOfSame=ListHolder.getInstance().returnFileName(viewPager.getCurrentItem());
        imageColor.init(viewLayout,fileName, fileNameForListOfSame);
        String AbsoluteFilePath=ListHolder.getInstance().getFilePath()+'/';

        final String filePath=AbsoluteFilePath+fileName;

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(filePath);
                boolean isDeleted = file.delete();
                if (isDeleted) {
                    if(!isColorClicked) {
                        makeToast("Image deleted");

                        ListHolder.getInstance().removeFileFromList(position);
                        ListHolder.getInstance().removeFileFromMap(fileName);
                        listSize--;
                        notifyDataSetChanged();
                        ListHolder.getInstance().notifyGrid();
                    }else{
                        ListHolder.getInstance().removeFileFromSameList(position);
                        ListHolder.getInstance().removeFileFromList(position);
                        ListHolder.getInstance().removeFileFromMap(fileName);
                        listSize--;
                        notifyDataSetChanged();
                        ListHolder.getInstance().notifyGridForSameColor();
                    }
                }
            }
        });

        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        myImage.setImageBitmap(bitmap);
        shareButton.setShareContent(sharePhoto(bitmap));
        new MyAnimation().setAnimation(context,myImage,400,R.anim.fade_in);

        container.addView(viewLayout);
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

    public void notifyForSame(){
        isColorClicked= ListHolder.getInstance().checkForSameMap();
        listSize=ListHolder.getInstance().calculateSizeOfGallery();
        notifyDataSetChanged();
    }

}
