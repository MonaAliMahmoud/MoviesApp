package com.mona.moviesapp.popular_people.FullImageScreen.view;

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
import com.mona.moviesapp.popular_people.FullImageScreen.controller.FullImageController;

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

    FullImageController fullImageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        full_img = findViewById(R.id.fullimg);
        saveImg = findViewById(R.id.savebtn);

        fullImageController = new FullImageController(this);

        picturePath = fullImageController.setPicturePath();
        fullImageController.getPicturePath(picturePath);

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

    public void setImage(Bitmap bitmap) {
        if(bitmap != null){
            full_img.setImageBitmap(bitmap);
        }
        else{
            full_img.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}
