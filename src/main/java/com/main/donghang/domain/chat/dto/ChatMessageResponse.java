package com.main.donghang.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ChatMessageResponse {

    private Long messageId;
    private Long roomId;
    private Long senderUserId;
    private String senderNickname;
    private String messageType;
    private String content;
    private LocalDateTime createdAt;

}
