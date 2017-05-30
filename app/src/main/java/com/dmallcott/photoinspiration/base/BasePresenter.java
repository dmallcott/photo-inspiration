package com.dmallcott.photoinspiration.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class BasePresenter<T extends BaseView> {

    private final CompositeDisposable attachedDisposables = new CompositeDisposable();
    private final CompositeDisposable visibleDisposables = new CompositeDisposable();
    private T view;

    protected BasePresenter() {
        // This class is designed for extension
    }

    /**
     * On view attached. To be called when your view is initialised.
     *
     * @param view View attached to the presenter
     */
    @CallSuper
    public void onViewAttached(@NonNull final T view) {
        if (isViewAttached()) {
            throw new IllegalStateException(
                    "View " + this.view + " is already attached. Cannot attach " + view);
        }
        this.view = view;
        Timber.i("View attached");
    }

    /**
     * On view detached. Intended as a cleanup process that should be called when the view will no
     * longer be in use.
     */
    @CallSuper
    public void onViewDetached() {
        if (!isViewAttached()) {
            throw new IllegalStateException("View is already detached");
        }
        view = null;
        attachedDisposables.clear();

        Timber.i("View detached");
    }

    /**
     * On view will show. Called when your view is about to be seen on the screen.
     */
    @CallSuper
    public void onViewWillShow(@NonNull final T view) {

    }

    /**
     * On view will hide. Called when your view is about to hide from the screen.
     */
    @CallSuper
    public void onViewWillHide() {
        visibleDisposables.clear();
    }

    /**
     * Dispose on view detach.
     *
     * @param disposable Disposable to be disposed of upon view detachment
     */
    @CallSuper
    protected void disposeOnViewDetach(@NonNull final Disposable disposable) {
        attachedDisposables.add(disposable);
    }

    /**
     * Dispose on view will hide.
     *
     * @param disposable Disposable to be disposed of upon view will hide
     */
    protected void disposeOnViewWillHide(@NonNull final Disposable disposable) {
        visibleDisposables.add(disposable);
    }

    public boolean isViewAttached() {
        return this.view != null;
    }
}
