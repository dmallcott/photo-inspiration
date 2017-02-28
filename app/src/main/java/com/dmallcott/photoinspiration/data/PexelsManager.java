package com.dmallcott.photoinspiration.data;

import android.support.annotation.NonNull;
import com.dmallcott.photoinspiration.data.json.PhotosResponse;
import com.dmallcott.photoinspiration.data.service.PexelsService;
import io.reactivex.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PexelsManager {

  @NonNull private final PexelsService service;

  @Inject
  public PexelsManager(@NonNull final PexelsService service) {
    this.service = service;
  }

  public Observable<PhotosResponse> getPopularPhotos(final int pageNumber) {
    return service.getPopularPhotos(pageNumber);
  }
}
