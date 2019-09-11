package com.mona.moviesapp.popular_people.PopularListScreen.model;

import com.mona.moviesapp.popular_people.PopularListScreen.controller.ListController;

public class ListModel {

    ListController listController;
    DataNetwork dataNetwork = new DataNetwork(this, listController);

    public ListModel(ListController listController) {
        this.listController = listController;
        this.dataNetwork = dataNetwork;
    }

    public void geturl(String popularurl){
        dataNetwork.execute(popularurl);
    }
}
