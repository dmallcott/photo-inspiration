package com.dmallcott.photoinspiration.application;

public class TestPhotoInspirationApplication extends PhotoInspirationApplication {

    @Override
    protected ApplicationComponent createComponent() {
        return DaggerTestApplicationComponent.builder()
                .testApplicationModule(new TestApplicationModule(this)).build();
    }
}