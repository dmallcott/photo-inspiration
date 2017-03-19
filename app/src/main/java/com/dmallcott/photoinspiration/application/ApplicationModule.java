package com.dmallcott.photoinspiration.application;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import com.dmallcott.photoinspiration.data.JsonManager;
import com.dmallcott.photoinspiration.data.PhotosRepository;
import com.dmallcott.photoinspiration.data.json.ApplicationAdapterFactory;
import com.dmallcott.photoinspiration.data.service.PexelsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import io.paperdb.Paper;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class ApplicationModule {

  public static final String IO_SCHEDULER = "io";
  public static final String UI_SCHEDULER = "ui";

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
    return new GsonBuilder()
        .registerTypeAdapterFactory(ApplicationAdapterFactory.create())
        .create();
  }

  @Provides
  PexelsService providePexelsService(Gson gson) {
    return PexelsService.Factory.makeService(gson);
  }

  @Provides
  @Singleton
  @Named(IO_SCHEDULER)
  Scheduler provideIoScheduler() {
    return Schedulers.io();
  }

  @Provides
  @Singleton
  @Named(UI_SCHEDULER)
  Scheduler provideUiScheduler() {
    return AndroidSchedulers.mainThread();
  }

  @Provides
  AssetManager provideAssetManager(Context context) {
    return context.getAssets();
  }

  @Provides
  JsonManager provideJsonManager(AssetManager assetManager, Gson gson) {
    return new JsonManager(assetManager, gson);
  }

  @Provides
  @Singleton
  PhotosRepository providePhotosRepository() {
    return new PhotosRepository(Paper.book(PhotosRepository.PHOTOS_BOOK));
  }
}
