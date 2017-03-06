package com.dmallcott.photoinspiration.data.model;

import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Message {

  public abstract String header();

  public abstract String message();

  public static Message create(@NonNull final String header, @NonNull final String message) {
    return new AutoValue_Message(header, message);
  }

  public static TypeAdapter<Message> typeAdapter(Gson gson) {
    return new AutoValue_Message.GsonTypeAdapter(gson);
  }
}
