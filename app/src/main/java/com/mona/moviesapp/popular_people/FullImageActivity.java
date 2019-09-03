package com.mona.moviesapp.popular_people;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mona.moviesapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class FullImageActivity extends AppCompatActivity {

    ImageView full_img;
    Button saveImg;

    String picturePath;
    URL ImageUrl = null;
    InputStream inputStream = null;
    Bitmap bmImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        full_img = findViewById(R.id.fullimg);
        saveImg = findViewById(R.id.savebtn);

        Intent intent = getIntent();
        Bundle bundle =  intent.getBundleExtra("data");
        if(!bundle.isEmpty()) {
            picturePath = bundle.getString("picture_path");
        }

        try {
            full_img.setImageBitmap(new loadImage(full_img).execute(picturePath).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FullImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                }
                else {
                    ActivityCompat.requestPermissions(FullImageActivity.this, new String[]
                            { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
                }
                saveImageToGallery();
            }
        });
    }

    private void saveImageToGallery(){
        full_img.setDrawingCacheEnabled(true);
        Bitmap b = full_img.getDrawingCache();
        MediaStore.Images.Media.insertImage(getContentResolver(), b, picturePath, "");
        Toast.makeText(FullImageActivity.this, "Saved to gallery", Toast.LENGTH_LONG).show();
    }

    public class loadImage extends AsyncTask<String, Void, Bitmap> {

        public loadImage(ImageView imageView) {

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
