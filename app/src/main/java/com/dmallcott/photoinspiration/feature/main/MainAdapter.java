package com.dmallcott.photoinspiration.feature.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dmallcott.photoinspiration.R;
import com.dmallcott.photoinspiration.data.model.Message;
import com.dmallcott.photoinspiration.data.model.Photo;
import com.dmallcott.photoinspiration.feature.main.views.MaskedGradientView;
import com.dmallcott.photoinspiration.feature.main.views.MaskedImageView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int PHOTO_TYPE = 0;
  private static final int MESSAGE_TYPE = 1;

  @NonNull private final Context context;
  @NonNull private final DisplayMetrics displayMetrics;
  @NonNull private final List<Object> dataSet;

  @Inject
  public MainAdapter(@NonNull final Context context, @NonNull final Display display) {
    this.context = context;
    this.displayMetrics = new DisplayMetrics();
    this.dataSet = new ArrayList<>();

    display.getRealMetrics(displayMetrics);
  }

  public void addAll(@NonNull final List<Photo> photos) {
    final int previousSize = dataSet.size();
    dataSet.addAll(photos);
    notifyItemRangeChanged(previousSize, dataSet.size());
  }

  public void addLoading(@NonNull final Message message) {
    final int previousSize = dataSet.size();
    dataSet.add(message);
    notifyItemRangeChanged(previousSize, dataSet.size());
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == PHOTO_TYPE) {
      return new MainViewHolder(
          LayoutInflater.from(context).inflate(R.layout.item_main_grid, parent, false)
      );
    } else {
      return new GradientViewHolder(
          LayoutInflater.from(context).inflate(R.layout.item_gradient, parent, false)
      );
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof MainViewHolder) {
      ((MainViewHolder) holder).photo.setPhoto((Photo) dataSet.get(position));
    } else if (holder instanceof GradientViewHolder) {
      ((GradientViewHolder) holder).gradient.setFirst(position == 0);
      ((GradientViewHolder) holder).gradient.setHeader(((Message) dataSet.get(position)).header());
      ((GradientViewHolder) holder).gradient.setBody(((Message) dataSet.get(position)).message());
    }
  }

  @Override
  public int getItemViewType(int position) {
    return (dataSet.get(position) instanceof Photo) ? PHOTO_TYPE : MESSAGE_TYPE;
  }

  @Override
  public int getItemCount() {
    return dataSet.size();
  }

  static class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.photo) MaskedImageView photo;

    public MainViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  static class GradientViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.gradient) MaskedGradientView gradient;

    public GradientViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
