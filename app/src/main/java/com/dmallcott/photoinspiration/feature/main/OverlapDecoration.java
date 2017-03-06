package com.dmallcott.photoinspiration.feature.main;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.dmallcott.photoinspiration.feature.main.views.MaskedImageView;

public class OverlapDecoration extends RecyclerView.ItemDecoration {

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    if (state.getItemCount() != 0) {
      outRect.set(0, -MaskedImageView.OFFSET, 0, 0);
    }
  }
}
