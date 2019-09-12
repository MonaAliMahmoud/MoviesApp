package com.mona.moviesapp.popular_people.PopularListScreen.controller;

import com.mona.moviesapp.popular_people.PopularListScreen.model.ListModel;
import com.mona.moviesapp.popular_people.PopularListScreen.view.PopularListActivity;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;

public class ListController {

    private PopularListActivity popularListActivity;
    private ListModel listModel = new ListModel(this);

    String img_path = "https://image.tmdb.org/t/p/w500/";

    public ListController(PopularListActivity popularListActivity) {
        this.popularListActivity = popularListActivity;
        this.listModel = listModel;
    }

    public void changeAdapter(){
        popularListActivity.changeList();
    }

    public void getPopularList(PopularInfo popularInfo){
        popularListActivity.addPopularList(popularInfo);
    }

    public void callJson(int pagenum){
        String popularUrl= "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page="+pagenum;
        listModel.geturl(popularUrl);
    }

    public void searchingCall(String searchStr){
        String searchUrl="https://api.themoviedb.org/3/search/person?api_key=bd9eb9f62e484b7b3de4718afb6cd421&query="+searchStr;
        listModel.geturl(searchUrl);
    }
}
