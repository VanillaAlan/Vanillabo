package com.github.vanillabo.ui;

import com.github.vanillabo.R;
import com.github.vanillabo.ui.widget.MultiSwipeRefreshLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alan on 16/4/7.
 */
public abstract class BaseActivity extends AppCompatActivity implements MultiSwipeRefreshLayout.CanChildScrollUpCallback {

    @Nullable
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    protected abstract int provideContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        trySetupSwipeRefresh();
    }

    private void trySetupSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(
                    R.color.colorAccent);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestDataRefresh();
                }
            });

            if (mSwipeRefreshLayout instanceof MultiSwipeRefreshLayout) {
                MultiSwipeRefreshLayout mswrl = (MultiSwipeRefreshLayout) mSwipeRefreshLayout;
                mswrl.setCanChildScrollUpCallback(this);
            }
        }
    }

    public void requestDataRefresh() {

    }

    public void setRequestDataRefresh(boolean requestDataRefresh) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override public void run() {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        } else {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return false;
    }
}
