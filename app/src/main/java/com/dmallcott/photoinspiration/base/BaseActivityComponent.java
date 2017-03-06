package com.dmallcott.photoinspiration.base;

import com.dmallcott.photoinspiration.application.ApplicationComponent;
import com.dmallcott.photoinspiration.feature.main.MainActivity;
import dagger.Component;

@BaseActivityScope
@Component(dependencies = ApplicationComponent.class, modules = BaseActivityModule.class)
public interface BaseActivityComponent {

  void inject(MainActivity activity);
}
