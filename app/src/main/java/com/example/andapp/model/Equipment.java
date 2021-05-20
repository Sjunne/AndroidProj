package com.example.andapp.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Equipment {

    private String category;
    private int numberOfEquipment;
    private boolean socialDistancingBool;
    private Uri imageURL;

    public Equipment()
    {

    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumberOfEquipment() {
        return numberOfEquipment;
    }

    public void setNumberOfEquipment(int numberOfEquipment) {
        this.numberOfEquipment = numberOfEquipment;
    }

    public boolean isSocialDistancingBool() {
        return socialDistancingBool;
    }

    public void setSocialDistancingBool(boolean socialDistancingBool) {
        this.socialDistancingBool = socialDistancingBool;
    }

    public Uri getImageURL() {
        return imageURL;
    }

    public void setImageURL(Uri imageURL) {
        this.imageURL = imageURL;
    }

    public void setImageUrlLol(Uri result1) {
        imageURL = result1;
    }

    public void setImageBitMap(Bitmap bmp) {
    }
}

