package com.learnify.lms.listener;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class PresenceEventListener {

  private final SimpMessageSendingOperations messagingTemplate;

  public PresenceEventListener(SimpMessageSendingOperations messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }
}
