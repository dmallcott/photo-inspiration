package com.dmallcott.photoinspiration.application;

import android.content.Context;
import com.dmallcott.photoinspiration.base.BaseComponent;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends BaseComponent {
  Context context();
}
