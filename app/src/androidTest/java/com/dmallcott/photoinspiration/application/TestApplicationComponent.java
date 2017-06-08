package com.dmallcott.photoinspiration.application;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestApplicationModule.class})
public interface TestApplicationComponent extends ApplicationComponent {
}