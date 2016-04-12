package com.github.vanillabo.ui;

import com.bumptech.glide.Glide;
import com.github.vanillabo.Config;
import com.github.vanillabo.R;
import com.github.vanillabo.Util.VanillaboPrefs;
import com.github.vanillabo.api.WeiboUserShowService;
import com.github.vanillabo.model.AccessToken;
import com.github.vanillabo.model.WeiboUser;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by alan on 16/4/7.
 */
public abstract class NavigationViewActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_LOGIN = 801;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.toolbar)
    Toolbar mActionBarToolbar;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    private View mNavigationViewHeader;

    private CircleImageView mIvAvatar;

    private TextView mTvScreenName;

    private TextView mTvDesc;

    private TextView mTvFollowing;

    private TextView mTvFollower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mActionBarToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        getActionBarToolbar();
        mNavigationViewHeader = mNavigationView.getHeaderView(0);
        mIvAvatar = ButterKnife.findById(mNavigationViewHeader, R.id.iv_avatar);
        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VanillaboPrefs.getInstance(NavigationViewActivity.this).isLoggedIn()) {
                    gotoLogin();
                }
            }
        });
        mTvScreenName = ButterKnife.findById(mNavigationViewHeader, R.id.tv_screen_name);
        mTvDesc = ButterKnife.findById(mNavigationViewHeader, R.id.tv_desc);
        mTvFollower = ButterKnife.findById(mNavigationViewHeader, R.id.tv_followers);
        mTvFollowing = ButterKnife.findById(mNavigationViewHeader, R.id.tv_following);
        if (VanillaboPrefs.getInstance(this).isLoggedIn()) {
            renderUserInfo(VanillaboPrefs.getInstance(this).getWeiboUser());
        } else {
            mNavigationView.getMenu().clear();
            gotoLogin();
        }
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar != null) {
            setSupportActionBar(mActionBarToolbar);
        }
        return mActionBarToolbar;
    }

    private void gotoLogin() {
        startActivityForResult(new Intent(NavigationViewActivity.this, LoginActivity.class),
                REQUEST_CODE_LOGIN);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logout();
        } else {
            goToNavDrawerItem(id);
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void logout() {
        VanillaboPrefs.getInstance(this).logout();
        mIvAvatar.setImageResource(R.drawable.ic_avatar_def);
        mTvScreenName.setText("");
        mTvFollower.setText("");
        mTvFollowing.setText("");
        mTvDesc.setText("");
        mNavigationView.getMenu().clear();
    }

    private void goToNavDrawerItem(int item) {

    }

    /**
     * Enables back navigation for activities that are launched from the NavBar. See
     * {@code AndroidManifest.xml} to find out the parent activity names for each activity.
     */
    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOGIN) {
            if (resultCode == RESULT_OK) {
                getUserInfo();
            } else {

            }
        }
    }

    private void getUserInfo() {
        AccessToken accessToken = VanillaboPrefs.getInstance(this).getAccessToken();
        final WeiboUserShowService weiboUserShowService = new Retrofit.Builder()
                .baseUrl(Config.WEIBO_API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeiboUserShowService.class);
        final Call<WeiboUser> weiboUserCall = weiboUserShowService.getWeiboUser(
                accessToken.access_token,
                accessToken.uid);
        weiboUserCall.enqueue(new Callback<WeiboUser>() {
            @Override
            public void onResponse(Call<WeiboUser> call, Response<WeiboUser> response) {
                VanillaboPrefs.getInstance(NavigationViewActivity.this).setWeiboUser(response.body());
                mNavigationView.inflateMenu(R.menu.activity_main_drawer);
                renderUserInfo(response.body());
            }

            @Override
            public void onFailure(Call<WeiboUser> call, Throwable t) {
                Timber.e(t, "get user info error");
            }
        });
    }

    private void renderUserInfo(WeiboUser weiboUser) {
        Glide.with(this).load(weiboUser.avatar_large).crossFade().into(mIvAvatar);
        mTvScreenName.setText(weiboUser.screen_name);
        mTvDesc.setText(weiboUser.description);
        mTvFollowing.setText(getString(R.string.following, weiboUser.friends_count));
        mTvFollower.setText(getString(R.string.followers, weiboUser.followers_count));
    }
}
