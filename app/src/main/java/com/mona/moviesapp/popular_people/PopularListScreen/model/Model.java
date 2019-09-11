package com.mona.moviesapp.popular_people.PopularListScreen.model;

import com.mona.moviesapp.popular_people.PopularListScreen.controller.Controller;
import com.mona.moviesapp.popular_people.pojo.PopularList;

import java.util.ArrayList;

public class Model {

    Controller controller ;
    DataNetwork dataNetwork = new DataNetwork(this, controller);

    public Model(Controller controller) {
        this.controller = controller;
    }

    String searchString="https://api.themoviedb.org/3/search/person?api_key=bd9eb9f62e484b7b3de4718afb6cd421&query=";
    int pagenum = 1;
    String popularurl= "https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page="+pagenum;

    public Model(DataNetwork dataNetwork) {
        this.dataNetwork = dataNetwork;
    }

    public void geturl(int pagenum){
        dataNetwork.execute(popularurl);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }
}
