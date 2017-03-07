package com.dmallcott.photoinspiration.application;

import android.content.Context;
import android.content.res.AssetManager;
import com.dmallcott.photoinspiration.base.BaseComponent;
import com.dmallcott.photoinspiration.data.JsonManager;
import com.dmallcott.photoinspiration.data.PexelsManager;
import dagger.Component;
import io.reactivex.Scheduler;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends BaseComponent {

  Context context();

  PexelsManager pexelsManager();

  AssetManager assetManager();

  JsonManager jsonManager();

  @Named(ApplicationModule.IO_SCHEDULER)
  Scheduler ioScheduler();

  @Named(ApplicationModule.UI_SCHEDULER)
  Scheduler uiScheduler();
}
