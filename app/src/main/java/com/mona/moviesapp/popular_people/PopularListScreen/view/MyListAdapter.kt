package com.mona.moviesapp.popular_people.PopularListScreen.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.mona.moviesapp.R
import com.mona.moviesapp.popular_people.PopularDetailsScreen.view.PopularDetailsActivity
import com.mona.moviesapp.popular_people.PopularListScreen.controller.ListController
import com.mona.moviesapp.popular_people.pojo.PopularInfo

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList
import java.util.concurrent.ExecutionException

class MyListAdapter(private val info: ArrayList<PopularInfo>, private val context: Context) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private var inflater: LayoutInflater? = null
    var popularInfo: PopularInfo? = null
    var img_path = "https://image.tmdb.org/t/p/w500/"
    var ImageUrl: URL? = null
    var inputStream: InputStream? = null
    var bmImg: Bitmap? = null
    var popImg: ImageView? = null

    var listController: ListController? = null

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
        try {
            popImg!!.setImageBitmap(loadImages(popImg!!).execute(img_path + popularInfo!!.profile_path).get())
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
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

                //                    listController.getData(popularInf, intent);

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

    inner class loadImages(imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            var httpURLConnection: HttpURLConnection? = null

            try {
                ImageUrl = URL(urls[0])
                Log.i("URL", ImageUrl!!.toString())

                httpURLConnection = ImageUrl!!.openConnection() as HttpURLConnection
                httpURLConnection.connect()
                inputStream = httpURLConnection.inputStream
                bmImg = BitmapFactory.decodeStream(inputStream)

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return bmImg
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            super.onPostExecute(bitmap)
            if (bitmap != null) {
                popImg!!.setImageBitmap(bitmap)
            } else {
                popImg!!.setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }
}
