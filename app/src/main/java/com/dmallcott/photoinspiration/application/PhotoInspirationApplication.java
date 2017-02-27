package com.dmallcott.photoinspiration.application;

import android.app.Application;
import com.dmallcott.photoinspiration.BuildConfig;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import timber.log.Timber;

public class PhotoInspirationApplication extends Application {

  private ApplicationComponent applicationComponent = createComponent();

  @Override
  public void onCreate() {
    super.onCreate();
    initialiseLeakCanary();
    Stetho.initializeWithDefaults(this);
    setupTimber();
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
}
