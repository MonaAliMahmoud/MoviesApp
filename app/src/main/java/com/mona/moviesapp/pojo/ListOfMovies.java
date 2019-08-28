package com.mona.moviesapp.pojo;

import java.util.ArrayList;

public class ListOfMovies {
    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<Movie> results;

    public ArrayList<Movie> getMovies() {
        return results;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.results = movies;
    }

    public ListOfMovies() {
    }
}