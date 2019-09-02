package com.mona.moviesapp.popular_people;

import android.content.Context;
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

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    private ArrayList<PopularInfo> popularList;
    private Context context;
    private LayoutInflater inflater;

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
        final PopularInfo popularInfo = popularList.get(i);
        myHolder.popName.setText(popularInfo.getName());
        Log.i("Name",popularInfo.getName());
        myHolder.popDepart.setText(popularInfo.getKnown_for_department());
        Log.i("Department",popularInfo.getKnown_for_department());
        myHolder.popName.setText("https://image.tmdb.org/t/p/w500/"+popularInfo.getProfile_path());
        Log.i("Name","https://image.tmdb.org/t/p/w500/"+popularInfo.getProfile_path());
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView popName;
        TextView popDepart;

        public ViewHolder(View itemView) {
            super(itemView);
//            this.popImg = (ImageView) itemView.findViewById(R.id.popimg);
            this.popName = (TextView) itemView.findViewById(R.id.popnametxt);
            Log.i("Name",popName.toString());
            popDepart = (TextView) itemView.findViewById(R.id.popdeparttxt);
        }
    }
}
