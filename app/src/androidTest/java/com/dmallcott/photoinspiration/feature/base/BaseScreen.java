package com.dmallcott.photoinspiration.feature.base;

import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;

public class BaseScreen {

    protected static String getString(@StringRes int stringResourceId) {
        return InstrumentationRegistry.getTargetContext().getString(stringResourceId);
    }
}
