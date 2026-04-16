package com.main.donghang.domain.chat;

import com.main.donghang.domain.chat.dto.ChatRoomType;
import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_rooms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false, length = 30)
    private ChatRoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "is_closed", nullable = false)
    private boolean isClosed;

    public ChatRoom(
            ChatRoomType roomType,
            Post post,
            User createdBy,
            String title,
            Integer maxParticipants
    ) {
        this.roomType = roomType;
        this.post = post;
        this.createdBy = createdBy;
        this.title = title;
        this.maxParticipants = maxParticipants;
        this.isClosed = false;
    }


}
