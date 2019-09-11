package com.mona.moviesapp.popular_people.FullImageScreen.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.mona.moviesapp.popular_people.FullImageScreen.controller.FullImageController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FullImageNetwork extends AsyncTask<String, Void, Bitmap> {

    URL ImageUrl = null;
    FullImageModel fullImageModel;
    FullImageController fullImageController;
    Bitmap bmImg = null;
    InputStream inputStream= null;

    public FullImageNetwork(FullImageModel fullImageModel, FullImageController fullImageController){
        this.fullImageController = fullImageController;
        this.fullImageModel = fullImageModel;
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
        fullImageModel.fullImageController.getImage(bitmap);
    }
}
