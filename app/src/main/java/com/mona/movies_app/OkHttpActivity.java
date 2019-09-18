package com.mona.movies_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    private TextView okData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        okData = findViewById(R.id.txt_ok_data);

        String url ="https://api.themoviedb.org/3/movie/550?api_key=bd9eb9f62e484b7b3de4718afb6cd421";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    final String result = response.body().string();

                    OkHttpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            okData.setText(result);
                        }
                    });
                }
            }
        });
    }
}
