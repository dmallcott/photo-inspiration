package com.dmallcott.photoinspiration.feature.main;

import com.dmallcott.photoinspiration.MessagesFactory;
import com.dmallcott.photoinspiration.data.LocalAssetsManager;
import com.dmallcott.photoinspiration.data.PexelsManager;
import com.dmallcott.photoinspiration.data.PhotosRepository;
import com.dmallcott.photoinspiration.data.json.PhotosResponse;
import com.dmallcott.photoinspiration.data.model.Message;
import com.dmallcott.photoinspiration.data.model.Photo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    PexelsManager pexelsManager;
    @Mock
    LocalAssetsManager localAssetsManager;
    @Mock
    PhotosRepository photosRepository;
    @Mock
    MainPresenter.View view;

    private MainPresenter presenter;

    private final List<Message> messages = MessagesFactory.getMessages(6);
    private final PublishSubject<Integer> pageSubject = PublishSubject.create();
    private final PublishSubject<Photo> photoSubject = PublishSubject.create();

    @Before
    public void setUp() throws Exception {

        when(view.onPageRequested()).thenReturn(pageSubject);
        when(view.onPhotoClicked()).thenReturn(photoSubject);
        when(localAssetsManager.getMessages()).thenReturn(messages);
        when(pexelsManager.getPopularPhotos(anyInt()))
                .thenReturn(Observable.just(mock(PhotosResponse.class)));

        presenter = new MainPresenter(pexelsManager, localAssetsManager, photosRepository,
                Schedulers.trampoline());
        presenter.onViewAttached(view);
    }

    @Test
    public void onViewAttached_requestFirstPage() throws Exception {
        verify(view).showLoading(messages.get(0));
        verify(view, times(2)).showPhotos(anyList()); // Happens twice due to persisted data
    }

    @Test
    public void onPageRequested_negativePage_nothingHappens() throws Exception {
        pageSubject.onNext(-1);

        // It happens only once due to the .startWith(1)
        verify(view, times(1)).showLoading(any(Message.class));
        verify(view, times(2)).showPhotos(anyList()); // Happens twice due to persisted data
    }
}