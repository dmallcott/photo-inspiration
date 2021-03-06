package com.dmallcott.photoinspiration.application;

import android.content.Context;
import android.content.res.AssetManager;

import com.dmallcott.photoinspiration.base.BaseComponent;
import com.dmallcott.photoinspiration.data.LocalAssetsManager;
import com.dmallcott.photoinspiration.data.PexelsManager;
import com.dmallcott.photoinspiration.data.PhotosRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends BaseComponent {

    Context context();

    PexelsManager pexelsManager();

    PhotosRepository photosRepository();

    AssetManager assetManager();

    LocalAssetsManager localAssetsManager();

    @Named(ApplicationModule.IO_SCHEDULER)
    Scheduler ioScheduler();

    @Named(ApplicationModule.UI_SCHEDULER)
    Scheduler uiScheduler();
}
