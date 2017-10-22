package com.dmallcott.photoinspiration.feature.main;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmallcott.photoinspiration.R;
import com.dmallcott.photoinspiration.data.model.Message;
import com.dmallcott.photoinspiration.data.model.Photo;
import com.dmallcott.photoinspiration.feature.main.views.MaskedGradientView;
import com.dmallcott.photoinspiration.feature.main.views.MaskedImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PHOTO_TYPE = 0;
    private static final int MESSAGE_TYPE = 1;

    @NonNull
    private final Context context;
    @NonNull
    private final DisplayMetrics displayMetrics;
    @NonNull
    private final List<Object> dataSet;

    private final PublishSubject<Photo> photoClickedSubject = PublishSubject.create();

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

    public void clearPhotos() {
        if (!dataSet.isEmpty()) {
            dataSet.subList(1, dataSet.size()).clear();
            notifyDataSetChanged();
        }
    }

    public Observable<Photo> onPhotoClicked() {
        return photoClickedSubject;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PHOTO_TYPE) {

            final View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_main_grid, parent, false);
            final MainViewHolder viewHolder = new MainViewHolder(view);

            view.setOnClickListener(ignored ->
                    photoClickedSubject.onNext((Photo) dataSet.get(viewHolder.getAdapterPosition())));

            return viewHolder;
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
            ((GradientViewHolder) holder).gradient.setMessage(((Message) dataSet.get(position)));
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

        @BindView(R.id.photo)
        MaskedImageView photo;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class GradientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gradient)
        MaskedGradientView gradient;

        public GradientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
