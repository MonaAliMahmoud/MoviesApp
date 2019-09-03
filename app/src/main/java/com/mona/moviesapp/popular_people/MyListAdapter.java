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
import android.widget.TextView;

import com.mona.moviesapp.R;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private ArrayList<PopularInfo> popularList;
    private Context context;
    private LayoutInflater inflater;
    PopularInfo popularInfo = null;
    String img_path = "https://image.tmdb.org/t/p/w500/";
    URL ImageUrl = null;
    InputStream inputStream = null;
    Bitmap bmImg = null;
    ImageView popImg = null;

    public MyListAdapter(ArrayList<PopularInfo> popularList, Context context) {
        this.context = context;
        inflater= LayoutInflater.from(context);
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ViewHolder myHolder= (ViewHolder) viewHolder;
        popularInfo = popularList.get(i);
        myHolder.popName.setText(popularInfo.getName());
        Log.i("Name",popularInfo.getName());
        myHolder.popDepart.setText(popularInfo.getKnown_for_department());
        Log.i("Department",popularInfo.getKnown_for_department());
        try {
            popImg.setImageBitmap(new loadImages(popImg).execute(img_path+popularInfo.getProfile_path()).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myHolder.bindData(popularInfo);
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView popName;
        TextView popDepart;

        public ViewHolder(final View itemView) {
            super(itemView);
            popImg = (ImageView) itemView.findViewById(R.id.popimg);
            this.popName = (TextView) itemView.findViewById(R.id.popnametxt);
            Log.i("Name", popName.toString());
            popDepart = (TextView) itemView.findViewById(R.id.popdeparttxt);
        }
        private void bindData(final PopularInfo popularInf){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PopularDetailsActivity.class);
                    Bundle arg = new Bundle();
                    arg.putString("popName", popularInf.getName());
                    arg.putString("popeDepart", popularInf.getKnown_for_department());
//                    arg.putBoolean("popAdult", popularInf.getAdult());
//                    arg.putInt("popGender", popularInf.getGender());
//                    arg.putFloat("popPopular", popularInf.getPopularity());
                    arg.putString("profile", img_path+popularInf.getProfile_path());
                    arg.putInt("id",popularInf.getId());
                    intent.putExtra("data", arg);
                    context.startActivity(intent);
                }
            });
        }
    }

    public class loadImages extends AsyncTask<String, Void, Bitmap>{

        public loadImages(ImageView imageView) {

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
                popImg.setImageBitmap(bitmap);
            }
            else{
                popImg.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }
}
