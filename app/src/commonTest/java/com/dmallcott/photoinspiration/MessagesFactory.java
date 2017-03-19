package com.dmallcott.photoinspiration;

import com.dmallcott.photoinspiration.data.model.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessagesFactory {

  private static final String[] titles = {"WELCOME", "HEY", "REMEMBER", "SMILE", "SOME ADVICE",
      "SIMPLE WORDS"};

  public static Message getMessage() {
    return Message
        .create(titles[new Random().nextInt(titles.length - 1)], "Ready for some inspiration?",
            "#8BFCFE", "#64A1FF");
  }

  public static List<Message> getMessages(final int size) {
    List<Message> messages = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      messages.add(getMessage());
    }
    return messages;
  }
}
