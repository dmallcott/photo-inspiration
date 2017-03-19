package com.dmallcott.photoinspiration.feature.detail;

import com.dmallcott.photoinspiration.base.BaseActivityScope;
import com.dmallcott.photoinspiration.base.BasePresenter;
import com.dmallcott.photoinspiration.base.BaseView;

import javax.inject.Inject;

@BaseActivityScope
public class DetailPresenter extends BasePresenter<DetailPresenter.View> {

    @Inject
    public DetailPresenter() {
    }

    public interface View extends BaseView {

    }
}
