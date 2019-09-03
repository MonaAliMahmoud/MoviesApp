package com.mona.moviesapp.popular_people;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.mona.moviesapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class FullImageActivity extends AppCompatActivity {

    ImageView full_img;
    String picturePath;

    URL ImageUrl = null;
    InputStream inputStream = null;
    Bitmap bmImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        full_img = findViewById(R.id.fullimg);

        Intent intent = getIntent();
        Bundle bundle =  intent.getBundleExtra("data");
        if(!bundle.isEmpty()) {
            picturePath = bundle.getString("picture_path");
        }

        try {
            full_img.setImageBitmap(new downloadImage(full_img).execute(picturePath).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            new downloadImage(full_img).execute(picturePath).get();
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
                full_img.setImageBitmap(bitmap);
            }
            else{
                full_img.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }
}
