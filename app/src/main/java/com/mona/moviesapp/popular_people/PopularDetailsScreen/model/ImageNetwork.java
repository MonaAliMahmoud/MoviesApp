package com.mona.moviesapp.popular_people.PopularDetailsScreen.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.mona.moviesapp.R;
import com.mona.moviesapp.popular_people.FullImageScreen.controller.FullImageController;
import com.mona.moviesapp.popular_people.FullImageScreen.model.FullImageModel;
import com.mona.moviesapp.popular_people.PopularDetailsScreen.controller.DetailsController;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImageNetwork extends AsyncTask<String, Void, Bitmap> {

    URL ImageUrl = null;
    DetailsModel detailsModel;
    private DetailsController detailsController;

    FullImageModel fullImageModel;
    FullImageController fullImageController;
    Bitmap bmImg = null;
    InputStream inputStream= null;

    public ImageNetwork(ImageView imageView) {
    }

    public ImageNetwork(DetailsModel detailsModel, DetailsController detailsController) {
        this.detailsController = detailsController;
        this.detailsModel = detailsModel;
    }

    public ImageNetwork(FullImageModel fullImageModel, FullImageController fullImageController){
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
        detailsModel.detailsController.getImage(bitmap);
    }
}
