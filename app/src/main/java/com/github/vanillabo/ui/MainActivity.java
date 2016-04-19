package com.github.vanillabo.ui;

import com.github.vanillabo.Config;
import com.github.vanillabo.R;
import com.github.vanillabo.Util.VanillaboPrefs;
import com.github.vanillabo.api.StatusService;
import com.github.vanillabo.model.Status;
import com.github.vanillabo.model.StatusResponse;
import com.github.vanillabo.ui.widget.DividerItemDecoration;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends NavigationViewActivity {

    @Bind(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    @Bind(R.id.rv_status)
    RecyclerView mRecyclerView;

    List<Status> mStatusList;

    StatusAdapter mStatusAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatusList = new ArrayList<>();
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        mStatusAdapter = new StatusAdapter(this, mStatusList);
        mRecyclerView.setAdapter(mStatusAdapter);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setRequestDataRefresh(true);
        requestDataRefresh();
    }

    @Override
    public void requestDataRefresh() {
        final StatusService statusService = new Retrofit.Builder()
                .baseUrl(Config.WEIBO_API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StatusService.class);
        final Call<StatusResponse> statusCall = statusService.getHomeTimeline(
                VanillaboPrefs.getInstance(this).getAccessToken().access_token,
                0,
                0,
                20,
                1);
        statusCall.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                setRequestDataRefresh(false);
                mStatusList.clear();
                mStatusList.addAll(response.body().statuses);
                mStatusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Timber.d("home timeline error %s", t.getMessage());
                setRequestDataRefresh(false);
            }
        });
    }

}
