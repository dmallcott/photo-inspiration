package com.dmallcott.photoinspiration.data;

import android.support.annotation.NonNull;
import com.dmallcott.photoinspiration.data.model.Photo;
import io.paperdb.Book;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/*
 * NOTE: I like using PaperDb for proof of concepts or just fast development since it's a good JSON dump.
 * When time allows I'll move to a better client side database implementation.
 */
@Singleton
public class PhotosRepository {

  public static final String PHOTOS_BOOK = "photos_book";

  @NonNull private final Book book;

  @Inject
  public PhotosRepository(@NonNull final Book book) {
    this.book = book;
  }

  public List<Photo> getSavedPhotos() {
    return Observable.fromIterable(book.getAllKeys())
        .map(key -> (Photo) book.read(key)).toList().blockingGet();
  }

  public void savePhotos(@NonNull final List<Photo> photos) {
    for (Photo photo : photos) {
      book.write(getCleanId(photo.url()), photo);
    }
  }

  public void clearSavedPhotos() {
    book.destroy();
  }

  private String getCleanId(@NonNull final String id) {
    return id.replaceAll("[^A-Za-z0-9 ]", "");
  }
}
