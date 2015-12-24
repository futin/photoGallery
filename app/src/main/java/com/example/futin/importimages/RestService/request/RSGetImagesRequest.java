package com.example.futin.importimages.RestService.request;

import com.example.futin.importimages.RestService.models.Image;

/**
 * Created by Futin on 12/22/2015.
 */
public class RSGetImagesRequest {
    Image images;

    public RSGetImagesRequest(Image images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "name="+images.getName()+"&url="+images.getUrl();
    }
}
