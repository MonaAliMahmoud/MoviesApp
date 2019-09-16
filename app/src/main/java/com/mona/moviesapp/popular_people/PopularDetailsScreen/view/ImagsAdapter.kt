package com.mona.moviesapp.popular_people.PopularDetailsScreen.view

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

import com.mona.moviesapp.R
import com.mona.moviesapp.popular_people.FullImageScreen.view.FullImageActivity
import com.mona.moviesapp.popular_people.pojo.Profiles

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList
import java.util.concurrent.ExecutionException

class ImagsAdapter(private val profiles: ArrayList<Profiles>, private val context: Context) : RecyclerView.Adapter<ImagsAdapter.ViewHolder>() {

    internal var img_path = "https://image.tmdb.org/t/p/w500/"
    internal var ImageUrl: URL? = null
    internal var inputStream: InputStream? = null
    internal var bmImg: Bitmap? = null
    internal var profileImg: ImageView? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val listItem = layoutInflater.inflate(R.layout.list_img, viewGroup, false)

        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        val profile_picture = profiles[i]

        try {
            profileImg!!.setImageBitmap(downloadImages(profileImg!!).execute(img_path + profile_picture.file_path).get())
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        viewHolder.bindData(profile_picture)
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

    inner class downloadImages(imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg strings: String): Bitmap? {

            try {
                ImageUrl = URL(strings[0])
                Log.i("URL", ImageUrl!!.toString())

                val conn = ImageUrl!!.openConnection() as HttpURLConnection
                conn.doInput = true
                conn.connect()
                inputStream = conn.inputStream
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
                profileImg!!.setImageBitmap(bitmap)
            } else {
                profileImg!!.setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }
}
