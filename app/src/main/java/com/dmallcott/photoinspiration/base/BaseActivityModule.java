package com.dmallcott.photoinspiration.base;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Display;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseActivityModule {

    private Activity activity;

    public BaseActivityModule(@NonNull final Activity activity) {
        this.activity = activity;
    }

    @Provides
    Display provideDisplay() {
        return activity.getWindowManager().getDefaultDisplay();
    }
}
