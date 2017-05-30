package com.dmallcott.photoinspiration.feature.main.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.dmallcott.photoinspiration.data.model.Message;

import java.util.Random;

import timber.log.Timber;

public class MaskedGradientView extends View {

    public final static int OFFSET = 120; // Forced to do this due to the way itemDecoration works
    private final static int MINIMUM_SLOPE = 40;
    protected final static int SHADOW_HEIGHT = 10;

    private final float ratio;
    private final float slope;

    private final Paint pathPaint;
    private final Paint shadowPaint;

    private final TextPaint textPaint;
    private final TextPaint bodyPaint;

    private final boolean isInverted;

    private boolean isFirst = false;
    private Message message;

    public MaskedGradientView(Context context) {
        this(context, null);
    }

    public MaskedGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        ratio = 1.78f; // 16:9

        final Random random = new Random();
        slope = random.nextFloat() * (OFFSET - MINIMUM_SLOPE) + MINIMUM_SLOPE;
        isInverted = random.nextBoolean();

        pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setColor(Color.WHITE);

        shadowPaint = new Paint();
        pathPaint.setAntiAlias(true);
        shadowPaint.setShadowLayer(SHADOW_HEIGHT, 0f, 0f, Color.BLACK);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(28 * getResources().getDisplayMetrics().density);
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        bodyPaint = new TextPaint();
        bodyPaint.setAntiAlias(true);
        bodyPaint.setTextSize(18 * getResources().getDisplayMetrics().density);
        bodyPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        pathPaint.setShader(new LinearGradient(0, 0, widthSize, 0,
                Color.parseColor(message.startColor()), Color.parseColor(message.endColor()),
                TileMode.CLAMP));

        // Based on a predetermined ratio the size is calculated from the 'match_parent' width
        setMeasuredDimension(widthSize, Math.round(widthSize / ratio));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Timber.i("Drawing gradient");
        canvas.drawPath(getPath(), shadowPaint);
        canvas.drawPath(getPath(), pathPaint);

        if (message != null) {
            // Adding text and aligning to the center
            StaticLayout headerlayout = new StaticLayout(message.header(), textPaint, canvas.getWidth(),
                    Alignment.ALIGN_CENTER, 1.0f, 0, false);
            canvas.translate((canvas.getWidth() / 2) - (headerlayout.getWidth() / 2),
                    (canvas.getHeight() / 2) - ((headerlayout.getHeight() / 2)));
            headerlayout.draw(canvas);
            StaticLayout bodyLayout = new StaticLayout(message.body(), bodyPaint, canvas.getWidth(),
                    Alignment.ALIGN_CENTER, 1.0f, 0, false);
            canvas.translate(0, headerlayout.getHeight());
            bodyLayout.draw(canvas);
        }
    }

    protected Path getPath() {

        Path path = new Path();

        // TODO : find a better way than this ugly conditional
        if (isFirst) {
            path.moveTo(0, 0);
            path.lineTo(0, getHeight());
            path.lineTo(getWidth(), getHeight());
            path.lineTo(getWidth(), 0);
            path.lineTo(0, 0);
            path.close();
        } else if (!isInverted) {
            path.moveTo(0, SHADOW_HEIGHT);
            path.lineTo(0, getHeight());
            path.lineTo(getWidth(), getHeight());
            path.lineTo(getWidth(), slope);
            path.lineTo(0, SHADOW_HEIGHT);
            path.close();
        } else {
            path.moveTo(getWidth(), SHADOW_HEIGHT);
            path.lineTo(getWidth(), getHeight());
            path.lineTo(0, getHeight());
            path.lineTo(0, slope);
            path.lineTo(getWidth(), SHADOW_HEIGHT);
            path.close();
        }

        return path;
    }

    public void setMessage(@NonNull final Message message) {
        this.message = message;
        invalidate();
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
