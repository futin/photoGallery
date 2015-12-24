package com.example.futin.importimages.RestService.response;

import com.example.futin.importimages.RestService.models.Image;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * Created by Futin on 12/22/2015.
 */
public class RSGetImagesResponse extends BaseResponse{
    ArrayList<Image> listOfImages;

    public RSGetImagesResponse(HttpStatus status, String statusName, ArrayList<Image> listOfImages) {
        super(status, statusName);
        this.listOfImages = listOfImages;
    }

    public RSGetImagesResponse(HttpStatus status, String statusName) {
        super(status, statusName);
    }

    public ArrayList<Image> getListOfImages() {
        return listOfImages;
    }

}
