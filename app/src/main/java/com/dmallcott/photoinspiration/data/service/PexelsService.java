package com.dmallcott.photoinspiration.data.service;

import android.support.annotation.NonNull;
import com.dmallcott.photoinspiration.BuildConfig;
import com.dmallcott.photoinspiration.data.json.PhotosResponse;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Observable;
import java.io.IOException;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
      final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      final AuthenticationInterceptor authentication = new AuthenticationInterceptor();
      logging.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);

      final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
          .addInterceptor(logging)
          .addInterceptor(authentication)
          .build();

      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(ENDPOINT)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .client(okHttpClient)
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();
      return retrofit.create(PexelsService.class);
    }
  }

  class AuthenticationInterceptor implements Interceptor {

    private static final String AUTH_HEADER = "Authorization";

    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request = chain.request().newBuilder()
          .addHeader(AUTH_HEADER, BuildConfig.PEXELS_API_KEY).build();
      return chain.proceed(request);
    }
  }
}
