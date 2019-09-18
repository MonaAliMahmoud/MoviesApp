package com.mona.movies_app.popular_people.popular_details_screen.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.mona.movies_app.R
import com.mona.movies_app.popular_people.full_image_screen.view.FullImageActivity
import com.mona.movies_app.popular_people.popular_details_screen.interfaces.DetailsViewInterface
import com.mona.movies_app.popular_people.popular_details_screen.model.DetailsModel
import com.mona.movies_app.popular_people.popular_details_screen.presenter.DetailsPresenter
import com.mona.movies_app.popular_people.pojo.Profiles

import java.util.ArrayList

class PopularDetailsActivity : AppCompatActivity(), DetailsViewInterface {

    private var name: TextView? = null
    private var depart: TextView? = null
    private var gender: TextView? = null
    private var popularity: TextView? = null
    private var adult: TextView? = null
    private var profile: ImageView? = null
    private var gridPhotos: RecyclerView? = null
    private lateinit var detailsAdapter: ImagesAdapter
    private var gridLayoutManager: GridLayoutManager? = null

    var detailsPresenter: DetailsPresenter? = null
    private var popularPictures = ArrayList<Profiles>()

    private var detailsIntent: Intent? = null
    private var bundle: Bundle? = null

    private var popName: String? = null
    private var popDepart: String? = null
    private var popProfile: String? = null
    private var popAdult: Boolean? = null
    private var popGender: Int = 0
    private var popId: Int = 0
    private var popPopular: Float = 0.toFloat()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_details)

        name = findViewById(R.id.txt_name)
        depart = findViewById(R.id.txt_depart)
        gender = findViewById(R.id.txt_gender)
        popularity = findViewById(R.id.txt_popularity)
        adult = findViewById(R.id.txt_adult)
        profile = findViewById(R.id.profile_img)
        gridPhotos = findViewById(R.id.photos)

        gridLayoutManager = GridLayoutManager(this@PopularDetailsActivity, 2)

        configGridRecycleView(popularPictures)

        detailsPresenter = DetailsPresenter(this, DetailsModel())

        detailsIntent = intent
        bundle = detailsIntent!!.getBundleExtra("data")

        if (!bundle!!.isEmpty) {
            popName = bundle!!.getString("popName")
            popDepart = bundle!!.getString("popeDepart")
            popAdult = bundle!!.getBoolean("popAdult")
            popGender = bundle!!.getInt("popGender")
            popPopular = bundle!!.getFloat("popPopular")
            popProfile = bundle!!.getString("profile")
            popId = bundle!!.getInt("id")
        }

        name!!.text = popName
        depart!!.text = popDepart
        adult!!.text = "Adult: " + popAdult!!
        gender!!.text = "Gender: $popGender"
        popularity!!.text = "Popularity: $popPopular"

        Glide.with(this)
                .load(popProfile)
                .into(profile!!)

        detailsPresenter!!.setPopId(popId)

        profile!!.setOnClickListener {
            val intent = Intent(this@PopularDetailsActivity, FullImageActivity::class.java)
            val arg = Bundle()
            arg.putString("picture_path", popProfile)
            intent.putExtra("data", arg)
            startActivity(intent)
        }
    }

    override fun configGridRecycleView(profiles: ArrayList<Profiles>) {
        detailsAdapter = ImagesAdapter(profiles, this@PopularDetailsActivity)
        gridPhotos!!.adapter = detailsAdapter
        gridPhotos!!.setHasFixedSize(true)
        gridPhotos!!.setItemViewCacheSize(20)
        gridPhotos!!.isDrawingCacheEnabled = true
        gridPhotos!!.layoutManager = gridLayoutManager
    }

    override fun changeGrid() {
        gridPhotos!!.setHasFixedSize(true)
        gridPhotos!!.setItemViewCacheSize(20)
        gridPhotos!!.isDrawingCacheEnabled = true
        detailsAdapter.notifyDataSetChanged()
        gridPhotos!!.layoutManager = gridLayoutManager
    }

    override fun addPopularDetails(profiles: Profiles) {
        popularPictures.add(profiles)
    }

    override fun goToFullImageScreen(imgPath: String, prof: Profiles) {
        val intent = Intent(this, FullImageActivity::class.java)
        val arg = Bundle()
        arg.putString("picture_path", imgPath + prof.file_path)
        intent.putExtra("data", arg)
        startActivity(intent)
    }
}
