package com.dmallcott.photoinspiration.feature.main;

import android.support.annotation.NonNull;

import com.dmallcott.photoinspiration.application.ApplicationModule;
import com.dmallcott.photoinspiration.base.BaseActivityScope;
import com.dmallcott.photoinspiration.base.BasePresenter;
import com.dmallcott.photoinspiration.base.BaseView;
import com.dmallcott.photoinspiration.data.LocalAssetsManager;
import com.dmallcott.photoinspiration.data.PexelsManager;
import com.dmallcott.photoinspiration.data.PhotosRepository;
import com.dmallcott.photoinspiration.data.json.PhotosResponse;
import com.dmallcott.photoinspiration.data.model.Message;
import com.dmallcott.photoinspiration.data.model.Photo;
import com.dmallcott.photoinspiration.feature.main.MainPresenter.View;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import timber.log.Timber;

@BaseActivityScope
public class MainPresenter extends BasePresenter<View> {

  @NonNull private final PexelsManager pexelsManager;
  @NonNull private final PhotosRepository photosRepository;
  @NonNull private final Scheduler uiScheduler;
  
  private final List<Message> messages;

  @Inject
  public MainPresenter(@NonNull final PexelsManager pexelsManager,
      @NonNull final LocalAssetsManager localAssetsManager,
      @NonNull final PhotosRepository photosRepository,
      @Named(ApplicationModule.UI_SCHEDULER) @NonNull final Scheduler uiScheduler) {

    this.pexelsManager = pexelsManager;
    this.photosRepository = photosRepository;
    this.uiScheduler = uiScheduler;
    this.messages = localAssetsManager.getMessages();
  }

  @Override
  public void onViewAttached(@NonNull View view) {
    super.onViewAttached(view);

    disposeOnViewDetach(
        view.onPageRequested()
            .startWith(1)
            .filter(current -> current >= 0)
            .doOnNext(current -> {
              view.showLoading(
                  current <= messages.size() ?
                      messages.get(current - 1) :
                      messages.get(1 + (current % messages.size()))
              );

              if (current == 1) {
                view.showPhotos(photosRepository.getSavedPhotos());
              }
            })
            .switchMap(pexelsManager::getPopularPhotos)
            .observeOn(uiScheduler)
            .doOnNext(response -> {
              if (response.page() == 1) {
                view.clearPhotos();
                photosRepository.clearSavedPhotos();
              }

              photosRepository.savePhotos(response.photos());
            })
            .doOnError(Timber::e)
            .onErrorResumeNext(Observable.empty())
            .map(PhotosResponse::photos)
            .subscribe(view::showPhotos)
    );

    disposeOnViewDetach(view.onPhotoClicked().doOnNext(view::openDetailView).subscribe());
  }

  interface View extends BaseView {

    void showLoading(Message message);

    void showPhotos(List<Photo> photos);

    void openDetailView(Photo photo);

    void clearPhotos();

    Observable<Integer> onPageRequested();

    Observable<Photo> onPhotoClicked();
  }
}
