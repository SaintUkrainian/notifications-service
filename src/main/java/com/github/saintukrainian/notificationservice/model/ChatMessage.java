package com.github.saintukrainian.notificationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage {

  private Long chatId;
  private String value;
  private BigInteger unseenMessagesCount;
  private UserDto fromUser;
}
