package com.mona.moviesapp.popular_people.PopularDetailsScreen.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mona.moviesapp.R;
import com.mona.moviesapp.popular_people.FullImageScreen.view.FullImageActivity;
import com.mona.moviesapp.popular_people.PopularDetailsScreen.controller.DetailsController;
import com.mona.moviesapp.popular_people.pojo.Profiles;

import java.util.ArrayList;

public class PopularDetailsActivity extends AppCompatActivity {

    private TextView name, depart, gender, popularity, adult;
    private ImageView profile;
    private RecyclerView gridphotos;
    ImagsAdapter adapter;

    DetailsController detailsController;
    ArrayList<Profiles> popularInfos = new ArrayList<Profiles>();

    private Intent intent;
    private Bundle bundle;

    private String popname, popdepart, popprofile;
    private Boolean popadult;
    private int popgender, popid;
    private float poppopular;

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
        gridphotos = findViewById(R.id.photos);

        configGridRecycleview(popularInfos);
        detailsController = new DetailsController(this);

        intent = getIntent();
        bundle =  intent.getBundleExtra("data");

        if(!bundle.isEmpty()){
            popname = bundle.getString("popName");
            popdepart = bundle.getString("popeDepart");
            popadult = bundle.getBoolean("popAdult");
            popgender = bundle.getInt("popGender");
            poppopular = bundle.getFloat("popPopular");
            popprofile = bundle.getString("profile");
            popid = bundle.getInt("id");
        }

        name.setText(popname);
        depart.setText(popdepart);
        adult.setText("Adult: "+ popadult);
        gender.setText("Gender: "+ popgender);
        popularity.setText("Popularity: "+ poppopular);
        detailsController.setPopId(popid);

        detailsController.getPopImage(popprofile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularDetailsActivity.this, FullImageActivity.class);
                Bundle arg = new Bundle();
                arg.putString("picture_path", popprofile);
                intent.putExtra("data", arg);
                startActivity(intent);
            }
        });
    }

    public void setImage(Object bitmap){
        if(bitmap != null){
            profile.setImageBitmap((Bitmap) bitmap);
        }
        else{
            profile.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    public void configGridRecycleview(ArrayList<Profiles> profiles){
        adapter = new ImagsAdapter(profiles, PopularDetailsActivity.this);
        gridphotos.setAdapter(adapter);
        gridphotos.setLayoutManager(new GridLayoutManager(PopularDetailsActivity.this, 2));
    }

    public void changeGride(){
        gridphotos.setHasFixedSize(true);
        gridphotos.setItemViewCacheSize(20);
        gridphotos.setDrawingCacheEnabled(true);
        adapter.notifyDataSetChanged();
        gridphotos.setLayoutManager(new GridLayoutManager(PopularDetailsActivity.this, 2));
    }

    public void addPopularDetails(Profiles profiles) {
        popularInfos.add(profiles);
    }
}
