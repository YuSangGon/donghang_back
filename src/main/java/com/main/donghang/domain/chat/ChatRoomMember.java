package com.main.donghang.domain.chat;

import com.main.donghang.domain.chat.dto.ChatRoomMemberRole;
import com.main.donghang.domain.common.BaseTimeEntity;
import com.main.donghang.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "chat_room_members",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_chat_room_member_room_user", columnNames = {"room_id", "user_id"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoomMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ChatRoomMemberRole role;

    public ChatRoomMember(ChatRoom room, User user, ChatRoomMemberRole role) {
        this.room = room;
        this.user = user;
        this.role = role;
    }
}
