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
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class LocalAssetsManager {

    public static final String JSON_MESSAGES = "messages.json";

    private AssetManager assetManager;
    private final Gson gson;

    public LocalAssetsManager(@NonNull final AssetManager assetManager, @NonNull final Gson gson) {
        this.assetManager = assetManager;
        this.gson = gson;
    }

    @NonNull
    public List<Message> getMessages() throws JsonProblemException {
        return Arrays.asList(getJson(assetManager, JSON_MESSAGES, Message[].class));
    }

    private <T> T getJson(@NonNull final AssetManager assetManager,
                          @NonNull final String name,
                          @NonNull final Class<T> c) throws JsonProblemException {
        try {
            final InputStream is = assetManager.open(name);
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.defaultCharset()));
            return gson.fromJson(reader, c);
        } catch (IOException e) {
            throw new JsonProblemException();
        }
    }

    public static class JsonProblemException extends RuntimeException {

    }
}
