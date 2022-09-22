package com.github.saintukrainian.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

  private Long chatId;
  private String latestMessage;

  public static Notification fromChatMessage(ChatMessage chatMessage) {
    return Notification.builder()
        .chatId(chatMessage.getChatId())
        .latestMessage(chatMessage.getValue())
        .build();
  }

  public static Notification fromDeletedChatMessage(ChatMessage chatMessage) {
    return Notification.builder()
        .chatId(chatMessage.getChatId())
        .latestMessage("*deleted*")
        .build();
  }
}
