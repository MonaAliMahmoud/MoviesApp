package com.mona.moviesapp.popular_people.pojo;

public class PopularInfo {
    private float popularity;
    private String known_for_department;
    private int gender;
    private int id;
    private String profile_path;
    private Boolean adult;
    private String name;

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
