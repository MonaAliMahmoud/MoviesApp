package com.mona.moviesapp.popular_people.PopularDetailsScreen.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.mona.moviesapp.popular_people.PopularDetailsScreen.model.DetailsModel;
import com.mona.moviesapp.popular_people.PopularDetailsScreen.view.PopularDetailsActivity;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;
import com.mona.moviesapp.popular_people.pojo.Profiles;

import java.util.ArrayList;

public class DetailsController {


    private PopularDetailsActivity popularDetailsActivity;
    private DetailsModel detailsModel = new DetailsModel(this);

    private String popname, popdepart, popprofile;
    private Boolean popadult;
    private int popgender, popid;
    private float poppopular;
    private Intent intent;
    private Bundle bundle;
    PopularInfo popularInfo = new PopularInfo();

    public DetailsController(PopularDetailsActivity popularDetailsActivity) {
        this.popularDetailsActivity = popularDetailsActivity;
        this.detailsModel = detailsModel;
    }

    public void getImage(Bitmap bitmap){
        popularDetailsActivity.setImage(bitmap);
    }

    public void conGridRecycle(ArrayList<Profiles> profiles){
        popularDetailsActivity.configGridRecycleview(profiles);
    }

    public void setPopId(int popularId){
       detailsModel.getPopId(popularId);
    }

    public PopularInfo setData(){

        intent = popularDetailsActivity.getIntent();
        bundle =  intent.getBundleExtra("data");

        if(!bundle.isEmpty()){
            popname = bundle.getString("popName");
            popdepart = bundle.getString("popeDepart");
            popadult = bundle.getBoolean("popAdult");
            popgender = bundle.getInt("popGender");
            poppopular = bundle.getFloat("popPopular");
            popprofile = bundle.getString("profile");
            popid = bundle.getInt("id");
        }
        popularInfo.setName(popname);
        popularInfo.setKnown_for_department(popdepart);
        popularInfo.setAdult(popadult);
        popularInfo.setGender(popgender);
        popularInfo.setPopularity(poppopular);
        popularInfo.setProfile_path(popprofile);
        popularInfo.setId(popid);

        return popularInfo;
    }
}
