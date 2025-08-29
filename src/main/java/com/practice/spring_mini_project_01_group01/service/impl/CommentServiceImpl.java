package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.common.utils.AuthUtil;
import com.practice.spring_mini_project_01_group01.dto.APIResponse;
import com.practice.spring_mini_project_01_group01.dto.comment.CommentRequest;
import com.practice.spring_mini_project_01_group01.dto.comment.CommentResponse;
import com.practice.spring_mini_project_01_group01.exception.NotFoundException;
import com.practice.spring_mini_project_01_group01.model.Article;
import com.practice.spring_mini_project_01_group01.model.Comment;
import com.practice.spring_mini_project_01_group01.model.User;
import com.practice.spring_mini_project_01_group01.repository.CommentRepository;
import com.practice.spring_mini_project_01_group01.repository.UserRepository;
import com.practice.spring_mini_project_01_group01.service.CommentService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final AuthUtil authUtil;

  @Override
  public APIResponse<CommentResponse> getCommentById(Long commentId) {
    User user = authUtil.getCurrentUser();

    Comment comment =
        commentRepository
            .findByIdAndUserId(commentId, user.getId())
            .orElseThrow(() -> new NotFoundException("Comment Id not found"));
    return new APIResponse<>(
        "Fetch My Comment Successfully", comment.toResponse(), HttpStatus.OK, LocalDateTime.now());
  }

  public void createComment(Article article, CommentRequest commentRequest) {
    User user = authUtil.getCurrentUser();

    Comment comment = new Comment();
    comment.setContent(commentRequest.getContent());
    comment.setArticle(article);
    comment.setUser(user);

    commentRepository.save(comment);
  }

  @Override
  @Transactional
  public APIResponse<CommentResponse> updateComment(Long commentId, CommentRequest commentRequest) {
    User user = authUtil.getCurrentUser();

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new NotFoundException("Comment Id not found"));

    if (!user.getId().equals(comment.getUser().getId())) {
      throw new AccessDeniedException("You do not have permission to delete this comment");
    }

    comment.setContent(commentRequest.getContent());
    comment.setUpdatedAt(LocalDateTime.now());

    return new APIResponse<>(
        "Updated Comment Successfully", comment.toResponse(), HttpStatus.OK, LocalDateTime.now());
  }

  @Override
  public APIResponse<CommentResponse> deleteComment(Long commentId) {
    User user = authUtil.getCurrentUser();

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new NotFoundException("Comment Id not found"));

    if (!user.getId().equals(comment.getUser().getId())) {
      throw new AccessDeniedException("You do not have permission to delete this comment");
    }

    commentRepository.delete(comment);
    return new APIResponse<>(
        "Deleted Comment Successfully", null, HttpStatus.OK, LocalDateTime.now());
  }
}
