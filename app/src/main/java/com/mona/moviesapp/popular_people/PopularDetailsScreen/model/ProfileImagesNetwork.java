package com.mona.moviesapp.popular_people.PopularDetailsScreen.model;

import android.os.AsyncTask;

import com.mona.moviesapp.popular_people.PopularDetailsScreen.controller.DetailsController;
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

public class ProfileImagesNetwork extends AsyncTask<String, String, String> {

    DetailsModel detailsModel;
    DetailsController detailsController;

    public ProfileImagesNetwork(DetailsModel detailsModel, DetailsController detailsController) {
        this.detailsModel = detailsModel;
        this.detailsController = detailsController;
    }

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

            detailsModel.detailsController.conGridRecycle(profiles);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

