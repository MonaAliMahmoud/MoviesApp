package com.mona.moviesapp.popular_people.PopularDetailsScreen.view

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView

import com.mona.moviesapp.R
import com.mona.moviesapp.popular_people.FullImageScreen.view.FullImageActivity
import com.mona.moviesapp.popular_people.PopularDetailsScreen.controller.DetailsController
import com.mona.moviesapp.popular_people.pojo.Profiles

import java.util.ArrayList

class PopularDetailsActivity : AppCompatActivity() {

    private var name: TextView? = null
    private var depart: TextView? = null
    private var gender: TextView? = null
    private var popularity: TextView? = null
    private var adult: TextView? = null
    private var profile: ImageView? = null
    private var gridphotos: RecyclerView? = null
    lateinit var detailsadapter: ImagsAdapter

    lateinit var detailsController: DetailsController
    internal var popularInfos = ArrayList<Profiles>()

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

        configGridRecycleview(popularInfos)
        detailsController = DetailsController(this)

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
        detailsController.setPopId(popid)

        detailsController.getPopImage(popprofile!!)

        profile!!.setOnClickListener {
            val intent = Intent(this@PopularDetailsActivity, FullImageActivity::class.java)
            val arg = Bundle()
            arg.putString("picture_path", popprofile)
            intent.putExtra("data", arg)
            startActivity(intent)
        }
    }

    fun setImage(bitmap: Any?) {
        if (bitmap != null) {
            profile!!.setImageBitmap(bitmap as Bitmap?)
        } else {
            profile!!.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    fun configGridRecycleview(profiles: ArrayList<Profiles>) {
        detailsadapter = ImagsAdapter(profiles, this@PopularDetailsActivity)
        gridphotos!!.adapter = detailsadapter
        gridphotos!!.layoutManager = GridLayoutManager(this@PopularDetailsActivity, 2)
    }

    fun changeGride() {
        gridphotos!!.setHasFixedSize(true)
        gridphotos!!.setItemViewCacheSize(20)
        gridphotos!!.isDrawingCacheEnabled = true
        detailsadapter.notifyDataSetChanged()
        gridphotos!!.layoutManager = GridLayoutManager(this@PopularDetailsActivity, 2)
    }

    fun addPopularDetails(profiles: Profiles) {
        popularInfos.add(profiles)
    }
}
