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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class PopularDetailsActivity extends AppCompatActivity {

    private TextView name, depart, gender, popularity, adult;
    private ImageView profile;
    private RecyclerView gridphotos;

    private String popname, popdepart, popprofile;
    private Boolean popadult;
    private int popgender, popid;
    private float poppopular;

    URL ImageUrl = null;
    InputStream inputStream = null;
    Bitmap bmImg = null;

    DetailsController detailsController;

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

        detailsController = new DetailsController(this);
        Intent intent = getIntent();
        Bundle bundle =  intent.getBundleExtra("data");

        if(!bundle.isEmpty()){
            popname = bundle.getString("popName");
            popdepart = bundle.getString("popeDepart");
            popadult = bundle.getBoolean("popAdult");
            popgender = bundle.getInt("popGender");
            poppopular = bundle.getFloat("popPopular");
            popprofile = bundle.getString("profile");
            popid = bundle.getInt("id");
        }
        String ad = Boolean.toString(popadult);
        String pop = Float.toString(poppopular);
        String gend = Float.toString(popgender);
        name.setText(popname);
        depart.setText(popdepart);
        adult.setText("Adult: "+ad);
        gender.setText("Gender: "+gend);
        popularity.setText("Popularity: "+pop);

        detailsController.setPopId(popid);

//        try {
//           profile.setImageBitmap(new loadImage(profile).execute(popprofile).get());
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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

//        new popularPhotos().execute("https://api.themoviedb.org/3/person/"+popid+"/images?api_key=bd9eb9f62e484b7b3de4718afb6cd421");
    }

    public int getPopId(){
        return popid;
    }

    public void setImage(Bitmap bitmap){
        if(bitmap != null){
            profile.setImageBitmap(bitmap);
        }
        else{
            profile.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    public void configGridRecycleview(ArrayList<Profiles> profiles){
        ImagsAdapter adapter = new ImagsAdapter(profiles, PopularDetailsActivity.this);
        gridphotos.setAdapter(adapter);
        gridphotos.setLayoutManager(new GridLayoutManager(PopularDetailsActivity.this, 2));
    }
}
