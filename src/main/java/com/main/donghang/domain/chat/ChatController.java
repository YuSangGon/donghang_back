package com.main.donghang.domain.chat;

import com.main.donghang.domain.chat.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/rooms/inquiry")
    public Map<String, Long> createOrGetInquiryRoom(@RequestBody CreateInquiryRoomRequest request) {
        Long roomId = chatService.createOrGetInquiryRoom(request.getPostId());
        return Map.of("roomId", roomId);
    }

    @PostMapping("/rooms/companion/{postId}")
    public Map<String, Long> createCompanionRoom(
            @PathVariable Long postId,
            @RequestParam Integer maxParticipants
    ) {
        Long roomId = chatService.createCompanionRoom(postId, maxParticipants);
        return Map.of("roomId", roomId);
    }

    @PostMapping("/rooms/{roomId}/join")
    public JoinChatRoomResponse joinCompanionRoom(@PathVariable Long roomId) {
        return chatService.joinCompanionRoom(roomId);
    }

    @GetMapping("/rooms/me")
    public List<ChatRoomResponse> getMyRooms() {
        return chatService.getMyRooms();
    }

    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable Long roomId) {
        return chatService.getMessages(roomId);
    }

    @PostMapping("/rooms/{roomId}/messages")
    public Map<String, Long> sendMessage(
            @PathVariable Long roomId,
            @RequestBody SendMessageRequest request
    ) {
        Long messageId = chatService.saveMessage(roomId, request.getContent());
        return Map.of("messageId", messageId);
    }

}
