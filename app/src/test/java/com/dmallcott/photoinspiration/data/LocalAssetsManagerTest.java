package com.dmallcott.photoinspiration.data;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import android.content.res.AssetManager;
import com.dmallcott.photoinspiration.MessagesFactory;
import com.dmallcott.photoinspiration.data.LocalAssetsManager.JsonProblemException;
import com.dmallcott.photoinspiration.data.model.Message;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class LocalAssetsManagerTest {

  @Mock AssetManager assetManager;
  @Mock Gson gson;

  private LocalAssetsManager manager;

  @Before
  public void setUp() throws Exception {
    manager = new LocalAssetsManager(assetManager, gson);
  }

  @Test(expected = JsonProblemException.class)
  public void onGetMessages_noJsonFile_jsonProblemThrown() throws Exception {
    when(assetManager.open(anyString())).thenThrow(new IOException());
    manager.getMessages();
  }

  @Test
  public void onGetMessages_messagesExists_returnMessages() throws Exception {
    final int size = 10;
    final List<Message> messages = MessagesFactory.getMessages(size);
    final Message[] messageArray = messages.toArray(new Message[size]);

    when(assetManager.open(anyString()))
        .thenReturn(new ByteArrayInputStream("".getBytes()));
    when(gson.fromJson(any(BufferedReader.class), any(Class.class))).thenReturn(messageArray);

    final List<Message> resultingMessages = manager.getMessages();

    Assert.assertEquals(messages, resultingMessages);
  }
}