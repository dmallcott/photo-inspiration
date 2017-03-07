package com.dmallcott.photoinspiration.feature.main.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.dmallcott.photoinspiration.data.model.Photo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import java.util.Random;
import timber.log.Timber;

public class MaskedImageView extends MaskedGradientView {

  private final Paint pathPaint;
  private Photo photo;
  private final Paint imagePaint;

  private float slope;
  private Bitmap bitmap;

  private Target target;
  private int offset;

  public MaskedImageView(Context context) {
    this(context, null);
  }

  public MaskedImageView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    imagePaint = new Paint();
    imagePaint.setColor(Color.WHITE);
    pathPaint = new Paint();
    pathPaint.setShadowLayer(SHADOW_HEIGHT, 0f, 0f, Color.BLACK);

    // Define random slope
    slope = 0.1f + (0.25f - 0.1f) * new Random().nextFloat();

    target = new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bmp, LoadedFrom from) {
        Timber.i("Bitmap loaded from %s", from.name());
        bitmap = bmp;

        invalidate();
      }

      @Override
      public void onBitmapFailed(Drawable errorDrawable) {
        Timber.e("Bitmap failed");
      }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {

      }
    };
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    int width = 0;
    int height = 0;

    if (photo != null) {
      final float ratio = (float) widthSize / photo.width();

      width = widthSize;
      height = Math.round(photo.height() * ratio);
      offset = - Math.round(height * slope);
      Timber.i("Measuring %s with w: %d and h: %d", photo.url(), width, height);
      Picasso.with(getContext()).load(photo.src().original()).resize(width, height).into(target);
      setMeasuredDimension(width, height);
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    
    if (bitmap != null) {
      Timber.i("Drawing %s", photo.url());
      canvas.drawBitmap(processImage(bitmap), 0, 0, imagePaint);
    } else {
      canvas.drawPath(getPath(), imagePaint);
    }
  }

  private Bitmap processImage(@NonNull final Bitmap bitmap) {
    Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
    BitmapShader shader = new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);

    Canvas canvas = new Canvas(bmp);
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setShader(shader);
    canvas.drawPath(getPath(), paint);

    return bmp;
  }

  public void setPhoto(@NonNull final Photo photo) {
    if (this.photo != null) {
      Picasso.with(getContext()).cancelRequest(target);
    }
    this.photo = photo;
    bitmap = null;
    invalidate();
  }

}
