package com.dmallcott.photoinspiration.application;

import android.app.Application;

import com.dmallcott.photoinspiration.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;
import timber.log.Timber;

public class PhotoInspirationApplication extends Application {

    private ApplicationComponent applicationComponent = createComponent();

    @Override
    public void onCreate() {
        super.onCreate();
        initialiseLeakCanary();
        setupTimber();
        initialisePicasso();
    }

    protected ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initialiseLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initialisePicasso() {
        final int PICASSO_DISK_CACHE_SIZE = 1024 * 1024 * 50; // 50 Mb

        Paper.init(this);
        Picasso.Builder picassoBuilder = new Picasso.Builder(this)
                .memoryCache(new LruCache(PICASSO_DISK_CACHE_SIZE));

        Picasso.setSingletonInstance(picassoBuilder.build());
    }
}
