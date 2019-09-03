package com.mona.moviesapp.popular_people;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mona.moviesapp.R;
import com.mona.moviesapp.popular_people.pojo.Profiles;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ImagsAdapter extends RecyclerView.Adapter<ImagsAdapter.ViewHolder>{

    private ArrayList<Profiles> profiles;
    private Context context;

    String img_path = "https://image.tmdb.org/t/p/w500/";
    URL ImageUrl = null;
    InputStream inputStream = null;
    Bitmap bmImg = null;
    ImageView profileImg = null;

    public ImagsAdapter(ArrayList<Profiles> profiles, Context context) {
        this.profiles = profiles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_img, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ViewHolder myHolder= (ViewHolder) viewHolder;
        Profiles profile_picture = profiles.get(i);

        try {
            profileImg.setImageBitmap(new downloadImages(profileImg).execute(img_path+ profile_picture.getFile_path()).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myHolder.bindData(profile_picture);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImg = (ImageView) itemView.findViewById(R.id.profileimg);
        }
        private void bindData(final Profiles prof){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullImageActivity.class);
                    Bundle arg = new Bundle();
                    arg.putString("picture_path", img_path+prof.getFile_path());
                    intent.putExtra("data", arg);
                    context.startActivity(intent);
                }
            });
        }
    }

    public class downloadImages extends AsyncTask<String, Void, Bitmap>
    {

        public downloadImages(ImageView imageView) {

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
                profileImg.setImageBitmap(bitmap);
            }
            else{
                profileImg.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }
}
