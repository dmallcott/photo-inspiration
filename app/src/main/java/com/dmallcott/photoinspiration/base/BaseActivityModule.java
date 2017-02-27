package com.dmallcott.photoinspiration.base;

import android.app.Activity;
import android.support.annotation.NonNull;
import dagger.Module;

@Module
public class BaseActivityModule {

  private Activity activity;

  public BaseActivityModule(@NonNull final Activity activity) {
    this.activity = activity;
  }

}
