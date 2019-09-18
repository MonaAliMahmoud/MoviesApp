package com.mona.movies_app.popular_people.popular_list_screen.view

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

import com.mona.movies_app.R
import com.mona.movies_app.popular_people.popular_details_screen.view.PopularDetailsActivity
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListViewInterface
import com.mona.movies_app.popular_people.popular_list_screen.presenter.ListPresenter
import com.mona.movies_app.popular_people.popular_list_screen.model.ListModel
import com.mona.movies_app.popular_people.pojo.PopularInfo

import java.util.ArrayList

class PopularListActivity : AppCompatActivity(), ListViewInterface {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var listLayoutManager: LinearLayoutManager

    private lateinit var listAdapter: MyListAdapter
    var listPresenter: ListPresenter? = null
    private var popularInfos = ArrayList<PopularInfo>()
    private var pageNum = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_list)

        swipeRefresh = findViewById(R.id.swipe_refresh)
        recyclerView = findViewById(R.id.my_recycler_view)

        listLayoutManager = LinearLayoutManager(this)
        configRecycleView(popularInfos)

        listPresenter = ListPresenter(this, ListModel())
        listPresenter!!.callJson(pageNum)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentItems = listLayoutManager.childCount
                val scrolledItems = listLayoutManager.findFirstCompletelyVisibleItemPosition()
                val totalItems = listLayoutManager.itemCount
                if (currentItems + scrolledItems == totalItems) {
                    pageNum++
                    listPresenter!!.callJson(pageNum)
                }
            }
        })

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed({
                swipeRefresh.isRefreshing = false
                listPresenter!!.callJson(pageNum)
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
                            listPresenter!!.searchingCall(s)
                        }
                        return true
                    }

                    override fun onQueryTextChange(s: String): Boolean {
                        popularInfos.clear()
                        if (searchView.query.isEmpty()) {
                            listPresenter!!.callJson(pageNum)
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
        recyclerView.layoutManager = listLayoutManager
    }

    override fun changeList() {
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.isDrawingCacheEnabled = true
        listAdapter.notifyDataSetChanged()
        recyclerView.layoutManager = listLayoutManager
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
