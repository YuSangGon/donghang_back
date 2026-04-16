package com.main.donghang.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinChatRoomResponse {

    private Long roomId;
    private boolean joined;

}
