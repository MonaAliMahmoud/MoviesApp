package com.mona.movies_app.popular_people.popular_list_screen.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mona.movies_app.R
import com.mona.movies_app.popular_people.pojo.PopularInfo
import java.util.*

class MyListAdapter(private val info: ArrayList<PopularInfo>, private var context: Context) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private var inflater: LayoutInflater? = null
    private var popularInfo: PopularInfo? = null
    private var imgPath = "https://image.tmdb.org/t/p/w500/"
    private var popImg: ImageView? = null

    private var popularListActivity = context as PopularListActivity

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
                .load(imgPath + popularInfo!!.profile_path)
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
            popImg = itemView.findViewById<View>(R.id.pop_img) as ImageView
            this.popName = itemView.findViewById<View>(R.id.txt_pop_name) as TextView
            Log.i("Name", popName.toString())
            popDepart = itemView.findViewById<View>(R.id.txt_pop_depart) as TextView
        }

        fun bindData(popularInf: PopularInfo) {
            itemView.setOnClickListener {
                popularListActivity.listPresenter!!.onItemViewClicked(popularInf)
            }
        }
    }
}
