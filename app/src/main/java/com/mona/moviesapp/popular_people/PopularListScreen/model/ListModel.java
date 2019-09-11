package com.mona.moviesapp.popular_people.PopularListScreen.model;

import com.mona.moviesapp.popular_people.PopularListScreen.controller.ListController;

public class ListModel {

    ListController listController;
    DataNetwork dataNetwork = new DataNetwork(this, listController);

    String searchString="https://api.themoviedb.org/3/search/person?api_key=bd9eb9f62e484b7b3de4718afb6cd421&query=";

    public ListModel(ListController listController) {
        this.listController = listController;
    }

    public ListModel(DataNetwork dataNetwork) {
        this.dataNetwork = dataNetwork;
    }

    public void geturl(int pagenum){
        String popularurl= "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page="+pagenum;
        dataNetwork.execute(popularurl);
    }

    public void setListController(ListController listController){
        this.listController = listController;
    }
}
