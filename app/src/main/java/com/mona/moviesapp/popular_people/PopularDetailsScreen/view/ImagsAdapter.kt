package com.mona.moviesapp.popular_people.PopularDetailsScreen.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mona.moviesapp.R
import com.mona.moviesapp.popular_people.FullImageScreen.view.FullImageActivity
import com.mona.moviesapp.popular_people.pojo.Profiles
import java.util.*

class ImagsAdapter(private val profiles: ArrayList<Profiles>, private val context: Context) : RecyclerView.Adapter<ImagsAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null
    internal var img_path = "https://image.tmdb.org/t/p/w500/"
    internal var profileImg: ImageView? = null
    var profile_picture:Profiles? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        inflater = LayoutInflater.from(viewGroup.context)
        val listItem = inflater!!.inflate(R.layout.list_img, viewGroup, false)

        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        profile_picture = profiles[i]

        Glide.with(this.context)
                .load(img_path + profile_picture!!.file_path)
                .into(profileImg!!)

        viewHolder.bindData(profile_picture!!)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            profileImg = itemView.findViewById<View>(R.id.profileimg) as ImageView
        }

        fun bindData(prof: Profiles) {
            itemView.setOnClickListener {
                val intent = Intent(context, FullImageActivity::class.java)
                val arg = Bundle()
                arg.putString("picture_path", img_path + prof.file_path)
                intent.putExtra("data", arg)
                context.startActivity(intent)
            }
        }
    }
}
