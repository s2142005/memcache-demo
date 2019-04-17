package com.networking.memcache.demo.app.presentation.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.networking.memcache.demo.R;
import com.networking.memcache.demo.app.DemoApplication;
import com.networking.memcache.demo.app.presentation.BaseActivity;
import com.networking.memcache.demo.app.presentation.adapter.RecyclerViewAdapter;
import com.networking.memcache.demo.app.widget.LoadingSpinner;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final String SEARCH_KEY = "Joshua";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fab) LoadingSpinner fabButton;
    @BindView(R.id.main_content) ConstraintLayout mainContentView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;

    private RecyclerViewAdapter adapter;
    private MainViewModel mainViewModel;
    private int pageOffset = 1;

    @Override
    public int contentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showProgressCircle();

        LinearLayoutManager linearLayoutMngr = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayoutMngr);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        observeUserListChangedEvent();
        initScrollListener();

        setupPullToRefreshEvent();
    }

    private void setupPullToRefreshEvent() {
        swipeLayout.setOnRefreshListener(() -> {
            DemoApplication.getInstance().getDataRepo().cleanUp();
            pageOffset = 0;
            loadMore();
        });

        // Scheme colors for animation
        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
    }

    public void showProgressCircle() {
        fabButton.setVisibility(View.VISIBLE);
    }

    public void hideProgressCircle() {
        swipeLayout.setRefreshing(false);
        fabButton.setVisibility(View.GONE);
    }

    private void observeUserListChangedEvent() {
        mainViewModel.fetchUsersData(SEARCH_KEY).observe(this, userList -> {
            adapter = new RecyclerViewAdapter(MainActivity.this, userList);
            recyclerView.setAdapter(adapter);
            hideProgressCircle();
        });
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if ((linearLayoutManager != null) &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                                (adapter.getItemCount() - 1)) {

                    showProgressCircle();
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        Observable.just(mainViewModel.loadMore(SEARCH_KEY, ++pageOffset))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listMutableLiveData -> {
                    int scrollToPos = listMutableLiveData.getValue().size() - 4;
                    recyclerView.scrollToPosition(scrollToPos);
                    hideProgressCircle();
                });
    }
}
