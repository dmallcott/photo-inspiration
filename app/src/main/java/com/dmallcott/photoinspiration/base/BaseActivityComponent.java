package com.dmallcott.photoinspiration.base;

import com.dmallcott.photoinspiration.application.ApplicationComponent;
import dagger.Component;

@BaseActivityScope
@Component(dependencies = ApplicationComponent.class, modules = BaseActivityModule.class)
public interface BaseActivityComponent {

}
