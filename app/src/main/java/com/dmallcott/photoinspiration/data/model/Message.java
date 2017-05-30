package com.dmallcott.photoinspiration.data.model;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Message {

    public abstract String header();

    public abstract String body();

    public abstract String startColor();

    public abstract String endColor();

    public static Message create(@NonNull final String header, @NonNull final String body,
                                 @NonNull final String startColor, @NonNull final String endColor) {
        return new AutoValue_Message(header, body, startColor, endColor);
    }

    public static TypeAdapter<Message> typeAdapter(Gson gson) {
        return new AutoValue_Message.GsonTypeAdapter(gson);
    }
}
