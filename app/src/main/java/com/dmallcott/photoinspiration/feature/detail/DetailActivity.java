package com.dmallcott.photoinspiration.feature.detail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.BindView;
import com.dmallcott.photoinspiration.R;
import com.dmallcott.photoinspiration.base.BaseActivity;
import com.dmallcott.photoinspiration.base.BaseActivityComponent;
import com.dmallcott.photoinspiration.base.BasePresenter;
import com.dmallcott.photoinspiration.data.model.Photo;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

public class DetailActivity extends BaseActivity<DetailPresenter.View> implements
    DetailPresenter.View {

  public static final String EXTRA_PHOTO = "extra_photo";

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.detailActivity_image) ImageView imageView;

  @Inject DetailPresenter presenter;

  public static Intent getStartIntent(@NonNull final Context context, @NonNull final Photo photo) {
    final Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(EXTRA_PHOTO, photo);
    return intent;
  }

  private Photo getPhotoFromIntent() {
    return (Photo) getIntent().getParcelableExtra(EXTRA_PHOTO);
  }

  @Override
  protected void onInitialize() {
    super.onInitialize();

    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    Picasso.with(this).load(getPhotoFromIntent().src().original()).into(imageView);
  }

  @Override
  protected void onInject(BaseActivityComponent component) {
    component.inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_detail;
  }

  @NonNull
  @Override
  protected BasePresenter<DetailPresenter.View> getPresenter() {
    return presenter;
  }

  @NonNull
  @Override
  protected DetailPresenter.View getPresenterView() {
    return this;
  }
}
