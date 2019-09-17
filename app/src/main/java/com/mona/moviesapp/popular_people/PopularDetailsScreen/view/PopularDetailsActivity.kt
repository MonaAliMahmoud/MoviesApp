package com.mona.moviesapp.popular_people.PopularDetailsScreen.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.mona.moviesapp.R
import com.mona.moviesapp.popular_people.FullImageScreen.view.FullImageActivity
import com.mona.moviesapp.popular_people.PopularDetailsScreen.Interfaces.DetailsViewInterface
import com.mona.moviesapp.popular_people.PopularDetailsScreen.model.DetailsModel
import com.mona.moviesapp.popular_people.PopularDetailsScreen.presenter.DetailsPresenter
import com.mona.moviesapp.popular_people.pojo.Profiles

import java.util.ArrayList

class PopularDetailsActivity : AppCompatActivity(), DetailsViewInterface {

    private var name: TextView? = null
    private var depart: TextView? = null
    private var gender: TextView? = null
    private var popularity: TextView? = null
    private var adult: TextView? = null
    private var profile: ImageView? = null
    private var gridphotos: RecyclerView? = null
    lateinit var detailsadapter: ImagsAdapter
    var gridLayoutManager: GridLayoutManager? = null

    var detailsPresenter: DetailsPresenter? = null
    internal var popularPictures = ArrayList<Profiles>()

    private var detailsIntent: Intent? = null
    private var bundle: Bundle? = null

    private var popname: String? = null
    private var popdepart: String? = null
    private var popprofile: String? = null
    private var popadult: Boolean? = null
    private var popgender: Int = 0
    private var popid: Int = 0
    private var poppopular: Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_details)

        name = findViewById(R.id.nametxt)
        depart = findViewById(R.id.departtxt)
        gender = findViewById(R.id.gendertxt)
        popularity = findViewById(R.id.popularitytxt)
        adult = findViewById(R.id.adulttxt)
        profile = findViewById(R.id.profileimg)
        gridphotos = findViewById(R.id.photos)

        gridLayoutManager = GridLayoutManager(this@PopularDetailsActivity, 2)

        configGridRecycleview(popularPictures)

        detailsPresenter = DetailsPresenter(this, DetailsModel())

        detailsIntent = intent
        bundle = detailsIntent!!.getBundleExtra("data")

        if (!bundle!!.isEmpty) {
            popname = bundle!!.getString("popName")
            popdepart = bundle!!.getString("popeDepart")
            popadult = bundle!!.getBoolean("popAdult")
            popgender = bundle!!.getInt("popGender")
            poppopular = bundle!!.getFloat("popPopular")
            popprofile = bundle!!.getString("profile")
            popid = bundle!!.getInt("id")
        }

        name!!.text = popname
        depart!!.text = popdepart
        adult!!.text = "Adult: " + popadult!!
        gender!!.text = "Gender: $popgender"
        popularity!!.text = "Popularity: $poppopular"

        Glide.with(this)
                .load(popprofile)
                .into(profile!!)

        detailsPresenter!!.setPopId(popid)

        profile!!.setOnClickListener {
            val intent = Intent(this@PopularDetailsActivity, FullImageActivity::class.java)
            val arg = Bundle()
            arg.putString("picture_path", popprofile)
            intent.putExtra("data", arg)
            startActivity(intent)
        }
    }

    override fun configGridRecycleview(profiles: ArrayList<Profiles>) {
        detailsadapter = ImagsAdapter(profiles, this@PopularDetailsActivity)
        gridphotos!!.adapter = detailsadapter
        gridphotos!!.setHasFixedSize(true)
        gridphotos!!.setItemViewCacheSize(20)
        gridphotos!!.isDrawingCacheEnabled = true
        gridphotos!!.layoutManager = gridLayoutManager
    }

    override fun changeGride() {
        gridphotos!!.setHasFixedSize(true)
        gridphotos!!.setItemViewCacheSize(20)
        gridphotos!!.isDrawingCacheEnabled = true
        detailsadapter.notifyDataSetChanged()
        gridphotos!!.layoutManager = gridLayoutManager
    }

    override fun addPopularDetails(profiles: Profiles) {
        popularPictures.add(profiles)
    }
}
