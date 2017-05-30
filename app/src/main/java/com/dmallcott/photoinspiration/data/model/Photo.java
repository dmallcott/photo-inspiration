package com.dmallcott.photoinspiration.data.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Photo implements Parcelable {

    public abstract int width();

    public abstract int height();

    public abstract String url();

    public abstract String photographer();

    public abstract Sources src();

    public static Photo create(final int width, final int height,
                               @NonNull final String url, @NonNull final String photographer,
                               @NonNull final Sources src) {
        return new AutoValue_Photo(width, height, url, photographer, src);
    }

    public static TypeAdapter<Photo> typeAdapter(Gson gson) {
        return new AutoValue_Photo.GsonTypeAdapter(gson);
    }

    @AutoValue
    public static abstract class Sources implements Parcelable {

        public abstract String original();

        public abstract String large();

        public abstract String medium();

        public abstract String small();

        public abstract String portrait();

        public abstract String landscape();

        public abstract String tiny();

        public static Sources create(@NonNull final String original, @NonNull final String large,
                                     @NonNull final String medium, @NonNull final String small,
                                     @NonNull final String portrait, @NonNull final String landscape, @NonNull final String tiny) {
            return new AutoValue_Photo_Sources(original, large, medium, small, portrait, landscape, tiny);
        }

        public static TypeAdapter<Sources> typeAdapter(@NonNull final Gson gson) {
            return new AutoValue_Photo_Sources.GsonTypeAdapter(gson);
        }
    }
}
