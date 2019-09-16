package com.mona.moviesapp.popular_people.PopularListScreen.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mona.moviesapp.R
import com.mona.moviesapp.popular_people.PopularDetailsScreen.view.PopularDetailsActivity
import com.mona.moviesapp.popular_people.pojo.PopularInfo
import java.util.*

class MyListAdapter(private val info: ArrayList<PopularInfo>, private val context: Context) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private var inflater: LayoutInflater? = null
    var popularInfo: PopularInfo? = null
    var img_path = "https://image.tmdb.org/t/p/w500/"
    var popImg: ImageView? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        inflater = LayoutInflater.from(viewGroup.context)
        val listItem = inflater!!.inflate(R.layout.list_item, viewGroup, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        popularInfo = info[i]
        viewHolder.popName.text = popularInfo!!.name
        Log.i("Name", popularInfo!!.name)
        viewHolder.popDepart.text = popularInfo!!.known_for_department
        Log.i("Department", popularInfo!!.known_for_department)

        Glide.with(this.context)
                .load(img_path + popularInfo!!.profile_path)
                .into(popImg!!)

        viewHolder.bindData(popularInfo!!)
    }

    override fun getItemCount(): Int {
        return info.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var popName: TextView
        internal var popDepart: TextView

        init {
            popImg = itemView.findViewById<View>(R.id.popimg) as ImageView
            this.popName = itemView.findViewById<View>(R.id.popnametxt) as TextView
            Log.i("Name", popName.toString())
            popDepart = itemView.findViewById<View>(R.id.popdeparttxt) as TextView
        }

        fun bindData(popularInf: PopularInfo) {
            itemView.setOnClickListener {
                val intent = Intent(context, PopularDetailsActivity::class.java)
                val arg = Bundle()
                arg.putString("popName", popularInf.name)
                arg.putString("popeDepart", popularInf.known_for_department)
                arg.putString("profile", img_path + popularInf.profile_path)
                arg.putInt("id", popularInf.id)
                intent.putExtra("data", arg)
                context.startActivity(intent)
            }
        }
    }
}
