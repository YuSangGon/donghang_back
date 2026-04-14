package com.main.donghang.domain.comment;

import com.main.donghang.domain.comment.dto.CommentCreateRequest;
import com.main.donghang.domain.comment.dto.CommentResponse;
import com.main.donghang.domain.comment.dto.CommentUpdateRequest;
import com.main.donghang.domain.post.Post;
import com.main.donghang.domain.post.PostRepository;
import com.main.donghang.domain.user.User;
import com.main.donghang.domain.user.UserRepository;
import com.main.donghang.global.auth.AuthUserUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthUserUtil authUserUtil;

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository,
            AuthUserUtil authUserUtil
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authUserUtil = authUserUtil;
    }

    private void validateCommentOwner(Comment comment) {
        Long currentUserId = authUserUtil.getCurrentUserId();
        if (!comment.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정 또는 삭제할 수 있습니다.");
        }
    }

    public Long create(Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Long currentUserId = authUserUtil.getCurrentUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 댓글입니다."));
        }

        Comment comment = new Comment(post, user, parentComment, request.getContent());

        return commentRepository.save(comment).getId();
    }

    public List<CommentResponse> getComments(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentResponse::new)
                .toList();
    }

    public Long update(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. id=" + commentId));
        validateCommentOwner(comment);
        comment.updateContent(request.getContent());
        return comment.getId();
    }

    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. id=" + commentId));
        validateCommentOwner(comment);
        commentRepository.delete(comment);
    }

}
