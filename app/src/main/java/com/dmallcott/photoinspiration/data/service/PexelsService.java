package com.dmallcott.photoinspiration.data.service;

import android.support.annotation.NonNull;
import com.dmallcott.photoinspiration.BuildConfig;
import com.dmallcott.photoinspiration.data.json.PhotosResponse;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Singleton
public interface PexelsService {

  String ENDPOINT = "http://api.pexels.com/v1/";

  @GET("popular")
  Observable<PhotosResponse> getPopularPhotos(@Query("page") int pageNumber);

  class Factory {

    public static PexelsService makeService(@NonNull final Gson gson) {
      final OkHttpClient okHttpClient = new OkHttpClient();
      final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

      logging.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);
      okHttpClient.interceptors().add(logging);

      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(ENDPOINT)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .client(okHttpClient)
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();
      return retrofit.create(PexelsService.class);
    }
  }
}
