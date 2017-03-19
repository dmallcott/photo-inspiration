package com.dmallcott.photoinspiration.data.json;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.dmallcott.photoinspiration.data.model.Photo;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@AutoValue
public abstract class PhotosResponse {

  public abstract int page();

  public abstract int per_page();

  public abstract int total_results();

  @SerializedName("prev_page")
  @Nullable
  public abstract String previousPage();

  @SerializedName("next_page")
  @Nullable
  public abstract String nextPage();

  public abstract List<Photo> photos();

  public static PhotosResponse create(final int page, final int per_page, final int total_results,
      @NonNull final String prev_page, @NonNull final String next_page,
      @NonNull final List<Photo> photos) {
    return new AutoValue_PhotosResponse(page, per_page, total_results, prev_page, next_page,
        photos);
  }

  public static TypeAdapter<PhotosResponse> typeAdapter(@NonNull final Gson gson) {
    return new AutoValue_PhotosResponse.GsonTypeAdapter(gson);
  }
}
