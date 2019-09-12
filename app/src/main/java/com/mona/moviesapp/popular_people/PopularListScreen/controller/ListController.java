package com.mona.moviesapp.popular_people.PopularListScreen.controller;

import com.mona.moviesapp.popular_people.PopularListScreen.model.ListModel;
import com.mona.moviesapp.popular_people.PopularListScreen.view.PopularListActivity;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;

import java.util.ArrayList;

public class ListController {

    private PopularListActivity popularListActivity;
    private ListModel listModel = new ListModel(this);

    String img_path = "https://image.tmdb.org/t/p/w500/";

    public ListController(PopularListActivity popularListActivity) {
        this.popularListActivity = popularListActivity;
        this.listModel = listModel;
    }

    public void conRecycle(ArrayList<PopularInfo> popularInfos){
        popularListActivity.configRecycleView(popularInfos);
    }

    public void changeAdapter(){
        popularListActivity.changeList();
    }

    public void getPopularList(PopularInfo popularInfo){
        popularListActivity.addPopularList(popularInfo);
    }

    public void callJson(int pagenum){
        String popularurl= "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page="+pagenum;
        listModel.geturl(popularurl);
    }

    public void searchingCall(String searchStr){
        String searchUrl="https://api.themoviedb.org/3/search/person?api_key=bd9eb9f62e484b7b3de4718afb6cd421&query="+searchStr;
        listModel.geturl(searchUrl);
    }

//    public Intent getData(PopularInfo popularInf){
//        Intent intent = new Intent(this, popularListActivity);
//        Bundle arg = new Bundle();
//        arg.putString("popName", popularInf.getName());
//        arg.putString("popeDepart", popularInf.getKnown_for_department());
//        arg.putString("profile", img_path+popularInf.getProfile_path());
//        arg.putInt("id",popularInf.getId());
//        intent.putExtra("data", arg);
//        return intent;
//    }
}
