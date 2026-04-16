package com.main.donghang.domain.chat;

import com.main.donghang.domain.chat.dto.ChatMessageType;
import com.main.donghang.domain.common.BaseTimeEntity;
import com.main.donghang.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_user_id")
    private User sender;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false, length = 20)
    private ChatMessageType messageType;

    @Lob
    @Column(nullable = false)
    private String content;

    public ChatMessage(
            ChatRoom room,
            User sender,
            ChatMessageType messageType,
            String content
    ) {
        this.room = room;
        this.sender = sender;
        this.messageType = messageType;
        this.content = content;
    }

}
