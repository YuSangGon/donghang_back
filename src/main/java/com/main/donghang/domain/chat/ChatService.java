package com.main.donghang.domain.chat;

import com.main.donghang.domain.market.MarketPostRepository;
import com.main.donghang.domain.post.PostRepository;
import com.main.donghang.domain.rent.RentPostRepository;
import com.main.donghang.domain.user.UserRepository;
import com.main.donghang.global.auth.AuthUserUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

}
