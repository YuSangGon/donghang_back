package com.main.donghang.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomResponse {

    private Long roomId;
    private String roomType;
    private Long postId;
    private String title;
    private Integer maxParticipants;
    private long currentParticipants;
    private boolean closed;


}
