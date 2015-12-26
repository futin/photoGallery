package com.example.futin.importimages.UserInterface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.futin.importimages.R;
import com.example.futin.importimages.UserInterface.animation.MyAnimation;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

/**
 * Created by Futin on 12/25/2015.
 */
public class SingleImageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    public SingleImageAdapter(Context context) {
        this.context=context;
    }

    @Override
    public int getCount() {
        return ListHolder.getInstance().calculateSizeOfGallery();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView myImage;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.single_image_adapter, container,
                false);
        myImage= (ImageView) viewLayout.findViewById(R.id.singleImageView);
        ShareButton shareButton= (ShareButton) viewLayout.findViewById(R.id.shareButton);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        String fileName=ListHolder.getInstance().returnFileName(position);
        String AbsolutefilePath=ListHolder.getInstance().getFilePath();
        String filePath=AbsolutefilePath.substring(0,AbsolutefilePath.lastIndexOf('/')+1);

        final Bitmap bitmap = BitmapFactory.decodeFile(filePath+fileName);
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
        ShareApi.share(content,null);
        return content;
    }


}
