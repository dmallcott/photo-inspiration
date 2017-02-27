package com.dmallcott.photoinspiration.application;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.dmallcott.photoinspiration.data.service.PexelsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
class ApplicationModule {

  private final Application application;

  ApplicationModule(@NonNull final Application application) {
    this.application = application;
  }

  @Provides
  Context provideContext() {
    return application;
  }

  @Provides
  @Singleton
  Gson provideGson() {
    return new GsonBuilder().create();
  }

  @Provides
  PexelsService providePexelsService(Gson gson) {
    return PexelsService.Factory.makeService(gson);
  }
}
