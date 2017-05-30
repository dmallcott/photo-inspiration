package com.dmallcott.photoinspiration.data;

import android.support.annotation.NonNull;

import com.dmallcott.photoinspiration.application.ApplicationModule;
import com.dmallcott.photoinspiration.data.json.PhotosResponse;
import com.dmallcott.photoinspiration.data.service.PexelsService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

@Singleton
public class PexelsManager {

    @NonNull
    private final PexelsService service;
    @NonNull
    private final Scheduler ioScheduler;

    @Inject
    public PexelsManager(@NonNull final PexelsService service,
                         @NonNull @Named(ApplicationModule.IO_SCHEDULER) final Scheduler ioScheduler) {
        this.service = service;
        this.ioScheduler = ioScheduler;
    }

    public Observable<PhotosResponse> getPopularPhotos(final int pageNumber) {
        return service.getPopularPhotos(pageNumber).subscribeOn(ioScheduler);
    }
}
