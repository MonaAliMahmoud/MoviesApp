package com.mona.moviesapp.popular_people;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mona.moviesapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class PopularDetailsActivity extends AppCompatActivity {

    private TextView name, depart, gender, popularity, adult;
    private ImageView profile;
    private GridView gridphotod;
    private String popname, popdepart, popprofile;
    private Boolean popadult;
    private int popgender, popid;
    private float poppopular;
    URL ImageUrl = null;
    InputStream inputStream = null;
    Bitmap bmImg = null;

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
        gridphotod = findViewById(R.id.photos);

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

}
