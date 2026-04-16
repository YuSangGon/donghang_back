package com.main.donghang.domain.chat;

import com.main.donghang.domain.chat.dto.*;
import com.main.donghang.domain.market.MarketPostRepository;
import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.post.PostCategory;
import com.main.donghang.domain.post.PostRepository;
import com.main.donghang.domain.rent.RentPostRepository;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import com.main.donghang.global.auth.AuthUserUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthUserUtil authUserUtil;
    private final RentPostRepository rentPostRepository;
    private final MarketPostRepository marketPostRepository;

    public ChatService(
            ChatRoomRepository chatRoomRepository,
            ChatRoomMemberRepository chatRoomMemberRepository,
            ChatMessageRepository chatMessageRepository,
            PostRepository postRepository,
            UserRepository userRepository,
            AuthUserUtil authUserUtil,
            RentPostRepository rentPostRepository,
            MarketPostRepository marketPostRepository
    ) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomMemberRepository = chatRoomMemberRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authUserUtil = authUserUtil;
        this.rentPostRepository = rentPostRepository;
        this.marketPostRepository = marketPostRepository;
    }

    public Long createOrGetInquiryRoom(Long postId) {
        Long currentUserId = authUserUtil.getCurrentUserId();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + postId));

        if (post.getCategory() != PostCategory.RENT && post.getCategory() != PostCategory.MARKET) {
            throw new IllegalArgumentException("문의 채팅은 렌트/중고 게시글에서만 생성할 수 있습니다.");
        }

        if (post.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("본인 글에는 문의 채팅을 생성할 수 없습니다.");
        }

        ChatRoom room = findExistingInquiryRoom(postId, currentUserId)
                .orElseGet(() -> createInquiryRoom(post, currentUserId));

        return room.getId();
    }

    private Optional<ChatRoom> findExistingInquiryRoom(Long postId, Long currentUserId) {
        List<ChatRoom> rooms = chatRoomRepository.findAll().stream()
                .filter(room -> room.getPost().getId().equals(postId))
                .filter(room -> room.getRoomType() == ChatRoomType.DIRECT_INQUIRY)
                .toList();

        return rooms.stream()
                .filter(room -> {
                    List<ChatRoomMember> members = chatRoomMemberRepository.findByRoomId(room.getId());
                    boolean hasCurrentUser = members.stream().anyMatch(member -> member.getUser().getId().equals(currentUserId));
                    boolean hasAuthor = members.stream().anyMatch(member -> member.getUser().getId().equals(room.getPost().getUser().getId()));
                    return hasCurrentUser && hasAuthor;
                })
                .findFirst();
    }

    private ChatRoom createInquiryRoom(Post post, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id=" + currentUserId));

        ChatRoom room = new ChatRoom(
                ChatRoomType.DIRECT_INQUIRY,
                post,
                post.getUser(),
                "[문의] " + post.getTitle(),
                2
        );

        ChatRoom savedRoom = chatRoomRepository.save(room);

        chatRoomMemberRepository.save(new ChatRoomMember(savedRoom, post.getUser(), ChatRoomMemberRole.HOST));
        chatRoomMemberRepository.save(new ChatRoomMember(savedRoom, currentUser, ChatRoomMemberRole.MEMBER));

        chatMessageRepository.save(new ChatMessage(
                savedRoom,
                null,
                ChatMessageType.SYSTEM,
                "문의 채팅방이 생성되었습니다."
        ));

        return savedRoom;
    }

    public Long createCompanionRoom(Long postId, Integer maxParticipants) {
        Long currentUserId = authUserUtil.getCurrentUserId();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + postId));

        if (post.getCategory() != PostCategory.DONGHANG) {
            throw new IllegalArgumentException("동행 게시글에만 그룹 채팅방을 만들 수 있습니다.");
        }

        if (!post.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("본인 동행 글에만 그룹 채팅방을 생성할 수 있습니다.");
        }

        if (chatRoomRepository.findByPostIdAndRoomType(postId, ChatRoomType.GROUP_COMPANION).isPresent()) {
            throw new IllegalArgumentException("이미 그룹 채팅방이 생성된 동행 글입니다.");
        }

        ChatRoom room = new ChatRoom(
                ChatRoomType.GROUP_COMPANION,
                post,
                post.getUser(),
                "[동행] " + post.getTitle(),
                maxParticipants
        );

        ChatRoom savedRoom = chatRoomRepository.save(room);

        chatRoomMemberRepository.save(new ChatRoomMember(savedRoom, post.getUser(), ChatRoomMemberRole.HOST));

        chatMessageRepository.save(new ChatMessage(
                savedRoom,
                null,
                ChatMessageType.SYSTEM,
                "동행 그룹 채팅방이 생성되었습니다."
        ));

        return savedRoom.getId();
    }

    public JoinChatRoomResponse joinCompanionRoom(Long roomId) {
        Long currentUserId = authUserUtil.getCurrentUserId();

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다. id=" + roomId));

        if (room.getRoomType() != ChatRoomType.GROUP_COMPANION) {
            throw new IllegalArgumentException("동행 그룹 채팅방만 참가할 수 있습니다.");
        }

        if (room.isClosed()) {
            throw new IllegalArgumentException("마감된 채팅방입니다.");
        }

        if (chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, currentUserId)) {
            return new JoinChatRoomResponse(roomId, true);
        }

        long currentCount = chatRoomMemberRepository.countByRoomId(roomId);
        if (room.getMaxParticipants() != null && currentCount >= room.getMaxParticipants()) {
            throw new IllegalArgumentException("정원이 마감되었습니다.");
        }

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id=" + currentUserId));

        chatRoomMemberRepository.save(new ChatRoomMember(room, user, ChatRoomMemberRole.MEMBER));

        chatMessageRepository.save(new ChatMessage(
                room,
                null,
                ChatMessageType.SYSTEM,
                user.getNickname() + "님이 입장했습니다."
        ));

        return new JoinChatRoomResponse(roomId, true);
    }

    public List<ChatRoomResponse> getMyRooms() {
        Long currentUserId = authUserUtil.getCurrentUserId();

        return chatRoomMemberRepository.findByUserIdOrderByCreatedAtDesc(currentUserId)
                .stream()
                .map(member -> {
                    ChatRoom room = member.getRoom();
                    long currentParticipants = chatRoomMemberRepository.countByRoomId(room.getId());

                    return new ChatRoomResponse(
                            room.getId(),
                            room.getRoomType().name(),
                            room.getPost().getId(),
                            room.getTitle(),
                            room.getMaxParticipants(),
                            currentParticipants,
                            room.isClosed()
                    );
                })
                .toList();
    }

    public List<ChatMessageResponse> getMessages(Long roomId) {
        validateMember(roomId, authUserUtil.getCurrentUserId());

        return chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId)
                .stream()
                .map(message -> new ChatMessageResponse(
                        message.getId(),
                        message.getRoom().getId(),
                        message.getSender() != null ? message.getSender().getId() : null,
                        message.getSender() != null ? message.getSender().getNickname() : null,
                        message.getMessageType().name(),
                        message.getContent(),
                        message.getCreatedAt()
                ))
                .toList();
    }

    public Long saveMessage(Long roomId, String content) {
        Long currentUserId = authUserUtil.getCurrentUserId();
        validateMember(roomId, currentUserId);

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("메시지 내용을 입력해주세요.");
        }

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다. id=" + roomId));

        User sender = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. id=" + currentUserId));

        ChatMessage message = new ChatMessage(
                room,
                sender,
                ChatMessageType.TEXT,
                content
        );

        return chatMessageRepository.save(message).getId();
    }

    private void validateMember(Long roomId, Long userId) {
        if (!chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, userId)) {
            throw new IllegalArgumentException("해당 채팅방의 참가자만 접근할 수 있습니다.");
        }
    }


}
