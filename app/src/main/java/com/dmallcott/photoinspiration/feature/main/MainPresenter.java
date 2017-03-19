package com.dmallcott.photoinspiration.feature.main;

import android.support.annotation.NonNull;

import com.dmallcott.photoinspiration.application.ApplicationModule;
import com.dmallcott.photoinspiration.base.BaseActivityScope;
import com.dmallcott.photoinspiration.base.BasePresenter;
import com.dmallcott.photoinspiration.base.BaseView;
import com.dmallcott.photoinspiration.data.LocalAssetsManager;
import com.dmallcott.photoinspiration.data.PexelsManager;
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
  @NonNull private final Scheduler uiScheduler;
  private final List<Message> messages;

  @Inject
  public MainPresenter(@NonNull final PexelsManager pexelsManager,
      @NonNull final LocalAssetsManager localAssetsManager,
      @Named(ApplicationModule.UI_SCHEDULER) @NonNull final Scheduler uiScheduler) {

    this.pexelsManager = pexelsManager;
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
            .doOnNext(current -> view.showLoading(
                current <= messages.size() ?
                    messages.get(current - 1) :
                    messages.get(1 + (current % messages.size()))
            ))
            .switchMap(pexelsManager::getPopularPhotos)
            .doOnError(Timber::e)
            .onErrorResumeNext(Observable.empty())
            .map(PhotosResponse::photos)
            .observeOn(uiScheduler)
            .subscribe(view::showPhotos)
    );
  }

  interface View extends BaseView {

    void showLoading(Message message);

    void showPhotos(List<Photo> photos);

    Observable<Integer> onPageRequested();
  }
}
