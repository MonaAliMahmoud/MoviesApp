package com.mona.moviesapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SerializableJavaActivity extends AppCompatActivity {

    TextView data;
    Button click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable_java);

        data = findViewById(R.id.datatxt);
        click = findViewById(R.id.btnClick);
        final String url ="https://api.themoviedb.org/3/movie/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&language=en-US&page=1";

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonData().execute(url);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class JsonData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream stream = httpURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String result = buffer.toString();
                JSONObject moviesList = new JSONObject(result);
                JSONArray movie = moviesList.getJSONArray("results");
                StringBuffer mlist = new StringBuffer();

                for (int i=0; i< movie.length(); i++) {
                    JSONObject movieResult = movie.getJSONObject(i);
                    String movieTitle = movieResult.getString("title");
                    mlist.append(movieTitle+"\n");
                }

                return mlist.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            data.setText(s);
        }
    }
}
