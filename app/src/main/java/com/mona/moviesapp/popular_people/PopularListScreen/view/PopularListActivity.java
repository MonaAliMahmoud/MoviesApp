package com.mona.moviesapp.popular_people.PopularListScreen.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.mona.moviesapp.R;
import com.mona.moviesapp.popular_people.PopularListScreen.controller.ListController;
import com.mona.moviesapp.popular_people.pojo.PopularInfo;

import java.util.ArrayList;

public class PopularListActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    MyListAdapter adapter;

    private int pagenum = 1;

    ListController listController;
    ArrayList<PopularInfo> popularInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_list);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        configRecycleView(popularInfos);
        listController = new ListController(this);
        listController.callJson(pagenum);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentItems = layoutManager.getChildCount();
                int scrolledItems = layoutManager.findFirstCompletelyVisibleItemPosition();
                int totalItems = layoutManager.getItemCount();
                if(currentItems + scrolledItems == totalItems) {
                    pagenum++;
                    listController.callJson(pagenum);
                }
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        refresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search_item);
        final SearchView searchView= (SearchView) item.getActionView();

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        popularInfos.clear();
                        if (! s.isEmpty()) {
                            listController.searchingCall(s);
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        popularInfos.clear();
                        if (searchView.getQuery().length() == 0){
                            listController.callJson(pagenum);
                        }
                        return true;
                    }
                });
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
               // new JsonData().execute("https://api.themoviedb.org/3/person/popular?api_key=bd9eb9f62e484b7b3de4718afb6cd421&page="+pagenum);
                return true;
            }
        });
        return true;
    }

    public void refresh(){
//        popularInfos.clear();
//        adapter.notifyDataSetChanged();
        listController.callJson(pagenum);
    }

    public void configRecycleView(ArrayList<PopularInfo> popularInfos){
        adapter = new MyListAdapter(popularInfos, PopularListActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void changeList(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
    }

    public  void addPopularList(PopularInfo popularInfo){
        popularInfos.add(popularInfo);
    }
}
