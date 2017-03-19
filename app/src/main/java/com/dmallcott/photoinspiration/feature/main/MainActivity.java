package com.dmallcott.photoinspiration.feature.main;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import butterknife.BindView;
import com.dmallcott.photoinspiration.R;
import com.dmallcott.photoinspiration.base.BaseActivity;
import com.dmallcott.photoinspiration.base.BaseActivityComponent;
import com.dmallcott.photoinspiration.base.BasePresenter;
import com.dmallcott.photoinspiration.data.model.Message;
import com.dmallcott.photoinspiration.data.model.Photo;
import com.dmallcott.photoinspiration.feature.detail.DetailActivity;
import com.dmallcott.photoinspiration.feature.main.MainPresenter.View;
import com.dmallcott.photoinspiration.feature.main.views.MaskedImageView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends BaseActivity<View> implements MainPresenter.View {

  @BindView(R.id.recycler_view_main) RecyclerView mainRecyclerView;

  @Inject MainPresenter presenter;
  @Inject MainAdapter adapter;

  private final PublishSubject<Integer> pageRequestSubject = PublishSubject.create();

  @Override
  protected void onInitialize() {
    final LayoutManager layoutManager = new PreCachingLayoutManager(this);
    final ItemDecoration decoration = new OverlapDecoration();
    final EndlessRecyclerViewScrollListener scrollListener =
        new EndlessRecyclerViewScrollListener(layoutManager) {
          @Override
          public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            Timber.i("Loading page %d", page);
            pageRequestSubject.onNext(page);
          }
        };

    mainRecyclerView.setLayoutManager(layoutManager);
    mainRecyclerView.addItemDecoration(decoration);
    mainRecyclerView.setAdapter(adapter);
    mainRecyclerView.setPadding(0, MaskedImageView.OFFSET, 0, 0);
    mainRecyclerView.addOnScrollListener(scrollListener);
  }

  @Override
  public void showLoading(Message message) {
    adapter.addLoading(message);
  }

  @Override
  public void showPhotos(List<Photo> photos) {
    adapter.addAll(photos);
  }

  @Override
  public void openDetailView(Photo photo) {
    startActivity(DetailActivity.getStartIntent(this, photo));
  }

  @Override
  public Observable<Integer> onPageRequested() {
    return pageRequestSubject;
  }

  @Override
  public Observable<Photo> onPhotoClicked() {
    return adapter.onPhotoClicked();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @NonNull
  @Override
  protected BasePresenter<View> getPresenter() {
    return presenter;
  }

  @NonNull
  @Override
  protected View getPresenterView() {
    return this;
  }

  @Override
  protected void onInject(BaseActivityComponent component) {
    component.inject(this);
  }
}
