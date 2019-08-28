package com.mona.moviesapp.pojo;

public class Movie {

    private float popularity;
    private int vote_count;
    private Boolean video;
    private String poster_path;
    private int id;
    private Boolean adult;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    private int []genre_ids;
    private String title;
    private float vote_average;
    private String overview;
    private String release_date;

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
