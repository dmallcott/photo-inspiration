package com.dmallcott.photoinspiration.feature.main.views;

import android.content.Context;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public abstract class MaskedView extends View {

    public final static int OFFSET = 120; // Forced to do this due to the way itemDecoration work
    public final static int SHADOW_HEIGHT = 10;

    public MaskedView(Context context) {
        super(context);
    }

    public MaskedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected Path getPath() {
        Path path = new Path();
        path.moveTo(0, SHADOW_HEIGHT);
        path.lineTo(0, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), OFFSET);
        path.lineTo(0, SHADOW_HEIGHT);
        path.close();

        return path;
    }
}
