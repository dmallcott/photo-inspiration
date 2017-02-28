package com.dmallcott.photoinspiration.data;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.dmallcott.photoinspiration.PhotosResponseFactory;
import com.dmallcott.photoinspiration.data.json.PhotosResponse;
import com.dmallcott.photoinspiration.data.service.PexelsService;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class PexelsManagerTest {

  @Mock PexelsService service;

  PexelsManager manager;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    manager = new PexelsManager(service);
  }

  @Test
  public void getPopularPhotos_nonNullReturn() throws Exception {

    final PhotosResponse photosResponse = PhotosResponseFactory.makePhotosResponse();

    when(service.getPopularPhotos(anyInt())).thenReturn(
        Observable.just(photosResponse)
    );

    TestObserver<PhotosResponse> result = new TestObserver<>();
    manager.getPopularPhotos(0).subscribe(result);
    result.assertNoErrors();
    result.assertValue(photosResponse);
  }
}