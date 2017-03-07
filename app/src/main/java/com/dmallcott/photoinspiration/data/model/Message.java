package com.dmallcott.photoinspiration.data.model;

import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Message {

  public abstract String header();

  public abstract String body();

  public static Message create(@NonNull final String header, @NonNull final String body) {
    return new AutoValue_Message(header, body);
  }

  public static TypeAdapter<Message> typeAdapter(Gson gson) {
    return new AutoValue_Message.GsonTypeAdapter(gson);
  }
}
