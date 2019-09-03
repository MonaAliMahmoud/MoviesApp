package com.mona.moviesapp.popular_people;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mona.moviesapp.R;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;
import com.mona.moviesapp.popular_people.pojo.PopularProfile;
import com.mona.moviesapp.popular_people.pojo.Profiles;

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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PopularDetailsActivity extends AppCompatActivity {

    private TextView name, depart, gender, popularity, adult;
    private ImageView profile;
    private RecyclerView gridphotos;

    private String popname, popdepart, popprofile;
    private Boolean popadult;
    private int popgender, popid;
    private float poppopular;

    URL ImageUrl = null;
    InputStream inputStream = null;
    Bitmap bmImg = null;

    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_details);

        name = findViewById(R.id.nametxt);
        depart = findViewById(R.id.departtxt);
        gender = findViewById(R.id.gendertxt);
        popularity = findViewById(R.id.popularitytxt);
        adult = findViewById(R.id.adulttxt);
        profile = findViewById(R.id.profileimg);
        gridphotos = findViewById(R.id.photos);

        Intent intent = getIntent();
        Bundle bundle =  intent.getBundleExtra("data");

        if(!bundle.isEmpty()){
            popname = bundle.getString("popName");
            popdepart = bundle.getString("popeDepart");
            popadult = bundle.getBoolean("popAdult");
            popgender = bundle.getInt("popGender");
            poppopular = bundle.getFloat("popPopular");
            popprofile = bundle.getString("profile");
            popid = bundle.getInt("id");
        }
        String ad = Boolean.toString(popadult);
        String pop = Float.toString(poppopular);
        String gend = Float.toString(popgender);
        name.setText(popname);
        depart.setText(popdepart);
        adult.setText("Adult: "+ad);
        gender.setText("Gender: "+gend);
        popularity.setText("Popularity: "+pop);

        try {
            profile.setImageBitmap(new downloadImage(profile).execute(popprofile).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new popularPhotos().execute("https://api.themoviedb.org/3/person/"+popid+"/images?api_key=bd9eb9f62e484b7b3de4718afb6cd421");
    }

    public class downloadImage extends AsyncTask<String, Void, Bitmap> {

        public downloadImage(ImageView imageView) {

        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                ImageUrl = new URL(strings[0]);
                Log.i("URL",ImageUrl.toString());

                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                inputStream = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null){
                profile.setImageBitmap(bitmap);
            }
            else{
                profile.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }

    public class popularPhotos extends AsyncTask<String, String, String> {

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
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ArrayList<Profiles> profiles = new ArrayList<>();
            try {
                JSONObject popular_profiles = new JSONObject(result);
                JSONArray pop_profiles = popular_profiles.getJSONArray("profiles");
                for (int i = 0; i< pop_profiles.length(); i++) {
                    JSONObject profileResult = pop_profiles.getJSONObject(i);
                    Profiles pictures = new Profiles();
                    pictures.setFile_path(profileResult.getString("file_path"));
                    profiles.add(pictures);
                }

                ImagsAdapter adapter = new ImagsAdapter(profiles, PopularDetailsActivity.this);
                gridphotos.setAdapter(adapter);
                gridphotos.setLayoutManager(new GridLayoutManager(PopularDetailsActivity.this, 2));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
