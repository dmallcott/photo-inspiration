package com.dmallcott.photoinspiration.data;


import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import com.dmallcott.photoinspiration.data.model.Message;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Singleton;

@Singleton
public class JsonManager {

  private static final String JSON_MESSAGES = "messages.json";

  private final AssetManager assetManager;
  private final Gson gson;

  public JsonManager(@NonNull final AssetManager assetManager, @NonNull final Gson gson) {
    this.assetManager = assetManager;
    this.gson = gson;
  }

  @NonNull
  public List<Message> getMessages() {
    final List<Message> messages = new ArrayList<>();

    try {
      messages.addAll(Arrays.asList(getJson(assetManager, JSON_MESSAGES, Message[].class)));
    } catch (JsonProblemException e) {
      // Do nothing for now
    }

    return messages;
  }

  @NonNull
  public <T> String serializeList(@NonNull final List<T> list) {
    return gson.toJson(list);
  }

  @NonNull
  <T> List<T> deserializeList(@NonNull final String list, @NonNull final Class<T[]> c) {
    return Arrays.asList(gson.fromJson(list, c));
  }

  private <T> T getJson(@NonNull final AssetManager assetManager,
      @NonNull final String name,
      @NonNull final Class<T> c) throws JsonProblemException {
    try {
      InputStream is = assetManager.open(name);
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(is, Charset.defaultCharset()));
      return gson.fromJson(reader, c);
    } catch (IOException e) {
      throw new JsonProblemException();
    }
  }

  private static class JsonProblemException extends RuntimeException {

  }
}
