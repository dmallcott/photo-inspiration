package com.dmallcott.photoinspiration.application;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import com.dmallcott.photoinspiration.data.LocalAssetsManager;
import com.dmallcott.photoinspiration.data.PhotosRepository;
import com.dmallcott.photoinspiration.data.service.PexelsService;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.dmallcott.photoinspiration.application.ApplicationModule.IO_SCHEDULER;
import static com.dmallcott.photoinspiration.application.ApplicationModule.UI_SCHEDULER;
import static org.mockito.Mockito.mock;

@Module
public class TestApplicationModule {
    private final Application application;

    TestApplicationModule(@NonNull final Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return mock(Gson.class);
    }

    @Provides
    PexelsService providePexelsService() {
        return mock(PexelsService.class);
    }

    @Provides
    @Singleton
    @Named(IO_SCHEDULER)
    Scheduler provideIoScheduler() {
        return Schedulers.trampoline();
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
    LocalAssetsManager provideJLocalAssetsManager(AssetManager assetManager, Gson gson) {
        return mock(LocalAssetsManager.class);
    }

    @Provides
    @Singleton
    PhotosRepository providePhotosRepository() {
        return mock(PhotosRepository.class);
    }
}