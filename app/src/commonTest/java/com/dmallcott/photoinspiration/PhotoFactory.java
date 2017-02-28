package com.dmallcott.photoinspiration;

import android.support.annotation.NonNull;
import com.dmallcott.photoinspiration.data.model.Photo;
import io.reactivex.Observable;
import java.util.List;

public class PhotoFactory {

  private static final String DEFAULT_URL = "https://www.pexels.com/photo/skyline-buildings-new-york-skyscrapers-2324/";
  private static final String DEFAULT_SOURCE_URL = "https://static.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg";
  private static final String DEFAULT_PHOTOGRAPHER = "Juan Perez";
  private static final int DEFAULT_WIDTH = 1920;
  private static final int DEFAULT_HEIGHT = 1080;


  public static Photo.Sources makeSources(@NonNull final String url) {
    return Photo.Sources.create(url, url, url, url, url, url, url);
  }

  public static Photo makePhoto() {
    return Photo.create(
        DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_URL, DEFAULT_PHOTOGRAPHER,
        makeSources(DEFAULT_SOURCE_URL)
    );
  }

  public static List<Photo> makePhotos(final int numberOfPhotos) {
    return Observable.range(0, numberOfPhotos).map(number -> makePhoto()).toList().blockingGet();
  }
}
