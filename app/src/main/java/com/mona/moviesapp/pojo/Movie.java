package com.mona.moviesapp.pojo;

import java.util.ArrayList;

public class Movie {
    float popularity;
    int vote_count;
    Boolean video;
    String poster_path;
    int id;
    Boolean adult;
    String backdrop_path;
    String original_language;
    String original_title;
    int []genre_ids;
    String title;
    float vote_average;
    String overview;
    String release_date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
