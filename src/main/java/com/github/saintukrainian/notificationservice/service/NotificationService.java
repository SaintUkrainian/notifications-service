package com.github.saintukrainian.notificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.saintukrainian.notificationservice.model.ChatMessage;
import com.github.saintukrainian.notificationservice.model.MessagesSeenRequest;
import com.github.saintukrainian.notificationservice.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

  private static final String NOTIFICATIONS_PATH = "/queue/notifications/";
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ObjectMapper mapper;

  @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic}")
  public void consumeMessage(String message) throws JsonProcessingException {
    ChatMessage chatMessage = mapper.readValue(message, ChatMessage.class);
    log.info("Received new message: {}", chatMessage);

    Notification notification = Notification.fromChatMessage(chatMessage);

    log.info("Sending notification: {}", notification);
    simpMessagingTemplate.convertAndSend(NOTIFICATIONS_PATH + chatMessage.getChatId(),
        notification);
  }

  @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "messages-seen")
  public void updateNewNotificationsCount(String request) throws JsonProcessingException {
    MessagesSeenRequest messagesSeenRequest = mapper.readValue(request,
        new TypeReference<>() {
        });
    log.info("Sending messages seen event for chat id: {}", messagesSeenRequest.getChatId());
    simpMessagingTemplate.convertAndSend(
        NOTIFICATIONS_PATH + messagesSeenRequest.getChatId() + "/messages-seen/"
            + messagesSeenRequest.getUserId(), true);
  }
}
