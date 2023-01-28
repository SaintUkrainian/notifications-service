package com.github.saintukrainian.notificationservice.model;

import java.math.BigInteger;
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
  private BigInteger unseenMessagesCount;
  private Long fromUserId;

  public static Notification fromChatMessage(ChatMessage chatMessage) {
    UserDto fromUser = chatMessage.getFromUser();
    return Notification.builder()
        .chatId(chatMessage.getChatId())
        .latestMessage(chatMessage.getValue())
        .unseenMessagesCount(chatMessage.getUnseenMessagesCount())
        .fromUserId(fromUser != null ? fromUser.getUserId() : null)
        .build();
  }

}
