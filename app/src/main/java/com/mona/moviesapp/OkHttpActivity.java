package com.mona.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    TextView okdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        okdata = findViewById(R.id.okdatatxt);

        final String url ="https://api.themoviedb.org/3/movie/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&language=en-US&page=1";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    final String result = response.body().string();

                    OkHttpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            okdata.setText(result);
                        }
                    });
                }
            }
        });
    }
}
