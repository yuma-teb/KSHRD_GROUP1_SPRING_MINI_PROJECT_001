package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.dto.commet.CommentRequest;
import com.practice.spring_mini_project_01_group01.dto.commet.CommentResponse;
import com.practice.spring_mini_project_01_group01.exception.NotFoundException;
import com.practice.spring_mini_project_01_group01.model.Article;
import com.practice.spring_mini_project_01_group01.model.Comment;
import com.practice.spring_mini_project_01_group01.model.User;
import com.practice.spring_mini_project_01_group01.repository.CommentRepository;
import com.practice.spring_mini_project_01_group01.repository.UserRepository;
import com.practice.spring_mini_project_01_group01.service.CommentService;
import com.practice.spring_mini_project_01_group01.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  @Override
  public CommentResponse getCommentById(Long commentId) {
    UserDetails currentUser = SecurityUtils.getCurrentUser();
    assert currentUser != null;

    User user = userRepository.findUserByEmail(currentUser.getUsername());

    Comment comment =
        commentRepository
            .findByIdAndUserId(commentId, user.getId())
            .orElseThrow(() -> new NotFoundException("Comment Id not found"));
    return comment.toResponse();
  }

  public void createComment(String content, Article article) {
    UserDetails currentUser = SecurityUtils.getCurrentUser();
    assert currentUser != null;
    User user = userRepository.findUserByEmail(currentUser.getUsername());

    Comment comment = new Comment();
    comment.setContent(content);
    comment.setArticle(article);
    comment.setUser(user);

    commentRepository.save(comment);
  }

  @Override
  @Transactional
  public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new NotFoundException("Comment Id not found"));

    comment.setContent(commentRequest.getContent());
    comment.setUpdatedAt(LocalDateTime.now());

    return comment.toResponse();
  }

  @Override
  public void deleteComment(Long commentId) {
    commentRepository.deleteById(commentId);
  }
}
