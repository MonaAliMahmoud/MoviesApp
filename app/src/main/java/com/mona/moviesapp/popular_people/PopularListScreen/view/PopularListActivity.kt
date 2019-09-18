package com.mona.moviesapp.popular_people.PopularListScreen.view

import android.content.Intent
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
import com.mona.moviesapp.popular_people.PopularDetailsScreen.view.PopularDetailsActivity
import com.mona.moviesapp.popular_people.PopularListScreen.Interfaces.ListViewInterface
import com.mona.moviesapp.popular_people.PopularListScreen.Presenter.ListPresenter
import com.mona.moviesapp.popular_people.PopularListScreen.model.ListModel
import com.mona.moviesapp.popular_people.pojo.PopularInfo

import java.util.ArrayList

class PopularListActivity : AppCompatActivity(), ListViewInterface {

    lateinit var swipeRefresh: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var listlayoutManager: LinearLayoutManager

    lateinit var listAdapter: MyListAdapter
    var listPresenter: ListPresenter? = null
    internal var popularInfos = ArrayList<PopularInfo>()
    private var pagenum = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_list)

        swipeRefresh = findViewById(R.id.swipe_refresh)
        recyclerView = findViewById(R.id.my_recycler_view)

        listlayoutManager = LinearLayoutManager(this)
        configRecycleView(popularInfos)

        listPresenter = ListPresenter(this, ListModel())
        listPresenter!!.callJson(pagenum)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentItems = listlayoutManager.childCount
                val scrolledItems = listlayoutManager.findFirstCompletelyVisibleItemPosition()
                val totalItems = listlayoutManager.itemCount
                if (currentItems + scrolledItems == totalItems) {
                    pagenum++
                    listPresenter!!.callJson(pagenum)
                }
            }
        })

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed({
                swipeRefresh.isRefreshing = false
                listPresenter!!.callJson(pagenum)
            }, 1000)
        }
    }

    fun getListPresenterObject(): ListPresenter?{
        return listPresenter
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
                            listPresenter!!.searchingCall(s)
                        }
                        return true
                    }

                    override fun onQueryTextChange(s: String): Boolean {
                        popularInfos.clear()
                        if (searchView.query.length == 0) {
                            listPresenter!!.callJson(pagenum)
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

    override fun configRecycleView(popularInfos: ArrayList<PopularInfo>) {
        listAdapter = MyListAdapter(popularInfos, this@PopularListActivity)
        recyclerView.adapter = listAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.layoutManager = listlayoutManager
    }

    override fun changeList() {
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.isDrawingCacheEnabled = true
        listAdapter.notifyDataSetChanged()
        recyclerView.layoutManager = listlayoutManager
    }

    override fun addPopularList(popularInfo: PopularInfo) {
        popularInfos.add(popularInfo)
    }

    override fun goToDetailsScreen(imgPath: String, popularInf: PopularInfo) {
        val intent = Intent(this, PopularDetailsActivity::class.java)
        val arg = Bundle()
        arg.putString("popName", popularInf.name)
        arg.putString("popeDepart", popularInf.known_for_department)
        arg.putString("profile", imgPath + popularInf.profile_path)
        arg.putInt("id", popularInf.id)
        intent.putExtra("data", arg)
        startActivity(intent)
    }
}
