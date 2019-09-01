package com.mona.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mona.moviesapp.popular_people.PopularListActivity;

public class MainActivity extends AppCompatActivity {

    Button httpurl, okhttp, popularPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httpurl = (Button) findViewById(R.id.btnHttpUrl);
        okhttp = (Button) findViewById(R.id.btnOkHttp);
        popularPeople = (Button) findViewById(R.id.btn_pop_people);

        httpurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HttpURLConnectionActivity.class));
            }
        });

        okhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OkHttpActivity.class));
            }
        });

        popularPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PopularListActivity.class));
            }
        });

    }
}
