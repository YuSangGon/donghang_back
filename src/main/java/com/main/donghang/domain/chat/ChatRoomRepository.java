package com.main.donghang.domain.chat;

import com.main.donghang.domain.chat.dto.ChatRoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByPostIdAndRoomType(Long postId, ChatRoomType roomType);

    List<ChatRoom> findByCreatedByIdOrderByCreatedAtDesc(Long userId);
}
