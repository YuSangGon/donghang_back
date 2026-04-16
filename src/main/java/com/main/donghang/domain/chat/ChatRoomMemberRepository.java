package com.main.donghang.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    boolean existsByRoomIdAndUserId(Long roomId, Long userId);

    long countByRoomId(Long roomId);

    Optional<ChatRoomMember> findByRoomIdAndUserId(Long roomId, Long userId);

    List<ChatRoomMember> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<ChatRoomMember> findByRoomId(Long roomId);

}
