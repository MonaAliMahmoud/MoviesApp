package com.mona.movies_app.popular_people.popular_details_screen.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mona.movies_app.R
import com.mona.movies_app.popular_people.pojo.Profiles
import java.util.*

class ImagesAdapter(private val profiles: ArrayList<Profiles>, private val context: Context) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null
    private var imgPath = "https://image.tmdb.org/t/p/w500/"
    private var profileImg: ImageView? = null
    private var profilePicture:Profiles? = null

    private var popularDetailsActivity = context as PopularDetailsActivity

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        inflater = LayoutInflater.from(viewGroup.context)
        val listItem = inflater!!.inflate(R.layout.list_img, viewGroup, false)

        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        profilePicture = profiles[i]

        Glide.with(this.context)
                .load(imgPath + profilePicture!!.file_path)
                .into(profileImg!!)

        viewHolder.bindData(profilePicture!!)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            profileImg = itemView.findViewById<View>(R.id.profile_img) as ImageView
        }

        fun bindData(prof: Profiles) {
            itemView.setOnClickListener {
                popularDetailsActivity.detailsPresenter!!.onItemViewClicked(prof)
            }
        }
    }
}
