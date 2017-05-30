package com.dmallcott.photoinspiration.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dmallcott.photoinspiration.application.ApplicationComponent;
import com.dmallcott.photoinspiration.application.PhotoInspirationApplication;

import butterknife.ButterKnife;

public abstract class BaseActivity<V extends BaseView> extends AppCompatActivity {

    private BaseActivityComponent activityComponent;

    /**
     * This class will wrap the Activity lifecycle so it handles certain operations by default thus
     * reducing boilerplate code. Overriding the #{@link AppCompatActivity#onCreate(Bundle)}
     * method so the default behaviour is as follows:
     * <p>
     * - The dagger component for the current module get's created
     * - The child class injects itself to the Dagger component
     * - The view gets inflated
     * - Through ButterKnife the views are bound
     * - onInitialize is called to notify the child activity that the view has been initialized
     * - The presenter is notified the view has been attached
     */
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createComponent();
        onInject(activityComponent);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        onInitialize();
        getPresenter().onViewAttached(getPresenterView());
    }

    @Override
    protected void onResume() {
        getPresenter().onViewWillShow(getPresenterView());
        super.onResume();
    }

    @Override
    protected void onPause() {
        getPresenter().onViewWillHide();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        getPresenter().onViewDetached();
        super.onDestroy();
    }

    protected void onInitialize() {

    }

    protected abstract void onInject(BaseActivityComponent component);

    @LayoutRes
    protected abstract int getLayoutId();

    @NonNull
    protected abstract BasePresenter<V> getPresenter();

    @NonNull
    protected abstract V getPresenterView();

    public ApplicationComponent getApplicationComponent() {
        return ((PhotoInspirationApplication) getApplication()).getApplicationComponent();
    }

    /**
     * For instrumentation testing purposes a method for overriding a BaseActivity's
     * component has been added. This is sadly because I can't have a test runner that override the
     * BaseActivity class. Or can I?
     */
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
}
