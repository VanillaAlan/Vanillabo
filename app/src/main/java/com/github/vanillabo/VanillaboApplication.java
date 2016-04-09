package com.github.vanillabo;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import android.app.Application;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by alan on 16/4/9.
 */
public class VanillaboApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Config.TWITTER_API_KEY,
                Config.TWITTER_BUILD_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Crashlytics());
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
