package com.mona.movies_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mona.movies_app.popular_people.popular_list_screen.view.PopularListActivity;

public class MainActivity extends AppCompatActivity {

    private Button httpUrl, okHttp, popularPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httpUrl = findViewById(R.id.btnHttpUrl);
        okHttp =  findViewById(R.id.btnOkHttp);
        popularPeople = findViewById(R.id.btn_pop_people);

        httpUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HttpURLConnectionActivity.class));
            }
        });

        okHttp.setOnClickListener(new View.OnClickListener() {
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
