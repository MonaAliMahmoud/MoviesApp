package com.mona.moviesapp.popular_people.PopularListScreen.view

import android.os.Bundle
import android.os.Handler
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem

import com.mona.moviesapp.R
import com.mona.moviesapp.popular_people.PopularListScreen.controller.ListController
import com.mona.moviesapp.popular_people.pojo.PopularInfo

import java.util.ArrayList

class PopularListActivity : AppCompatActivity() {

    lateinit var swipeRefresh: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    lateinit var adapter: MyListAdapter
    lateinit var listController: ListController
    internal var popularInfos = ArrayList<PopularInfo>()
    private var pagenum = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_list)

        swipeRefresh = findViewById(R.id.swipe_refresh)
        recyclerView = findViewById(R.id.my_recycler_view)

        layoutManager = LinearLayoutManager(this)
        configRecycleView(popularInfos)
        listController = ListController(this)
        listController.callJson(pagenum)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentItems = layoutManager.childCount
                val scrolledItems = layoutManager.findFirstCompletelyVisibleItemPosition()
                val totalItems = layoutManager.itemCount
                if (currentItems + scrolledItems == totalItems) {
                    pagenum++
                    listController.callJson(pagenum)
                }
            }
        })

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed({
                swipeRefresh.isRefreshing = false
                listController.callJson(pagenum)
            }, 1000)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val item: MenuItem = menu.findItem(R.id.search_item)
        val searchView: SearchView = item.actionView as SearchView

        MenuItemCompat.setOnActionExpandListener(item, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(s: String): Boolean {
                        popularInfos.clear()
                        if (!s.isEmpty()) {
                            listController.searchingCall(s)
                        }
                        return true
                    }

                    override fun onQueryTextChange(s: String): Boolean {
                        popularInfos.clear()
                        if (searchView.query.length == 0) {
                            listController.callJson(pagenum)
                        }
                        return true
                    }
                })
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                return true
            }
        })
        return true
    }

    fun configRecycleView(popularInfos: ArrayList<PopularInfo>) {
        adapter = MyListAdapter(popularInfos, this@PopularListActivity)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.layoutManager = layoutManager
    }

    fun changeList() {
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.isDrawingCacheEnabled = true
        adapter.notifyDataSetChanged()
        recyclerView.layoutManager = layoutManager
    }

    fun addPopularList(popularInfo: PopularInfo) {
        popularInfos.add(popularInfo)
    }
}
