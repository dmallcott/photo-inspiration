package com.dmallcott.photoinspiration.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.dmallcott.photoinspiration.application.ApplicationComponent;
import com.dmallcott.photoinspiration.application.PhotoInspirationApplication;

public abstract class BaseActivity<V extends BaseView> extends AppCompatActivity {

  private BaseActivityComponent activityComponent;

  @Override
  protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createComponent();
    inject(activityComponent);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
    getPresenter().onViewAttached(getPresenterView());
  }

  @Override
  protected void onDestroy() {
    getPresenter().onViewDetached();
    super.onDestroy();
  }

  public ApplicationComponent getApplicationComponent() {
    return ((PhotoInspirationApplication) getApplication()).getApplicationComponent();
  }

  public void setComponent(@NonNull final BaseActivityComponent component) {
    this.activityComponent = component;
  }

  /**
   * Override in case you want to use a custom component, otherwise it will return the
   * default base activity component.
   *
   * @return activity component
   */
  protected BaseActivityComponent createComponent() {
    if (activityComponent == null) {
      activityComponent = DaggerBaseActivityComponent.builder()
          .baseActivityModule(new BaseActivityModule(this))
          .applicationComponent(getApplicationComponent())
          .build();
    }

    return activityComponent;
  }

  @LayoutRes
  protected abstract int getLayoutId();

  @NonNull
  protected abstract BasePresenter<V> getPresenter();

  @NonNull
  protected abstract V getPresenterView();

  protected abstract void inject(BaseActivityComponent component);
}
