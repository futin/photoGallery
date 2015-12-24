package com.example.futin.importimages.RestService.models;

/**
 * Created by Futin on 12/22/2015.
 */
public class Image {
    String name;
    String url;

    public Image(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name+" "+url;
    }
}
