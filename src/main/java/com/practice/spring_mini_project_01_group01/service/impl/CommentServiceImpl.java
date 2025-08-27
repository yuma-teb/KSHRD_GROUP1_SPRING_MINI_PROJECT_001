package com.practice.spring_mini_project_01_group01.service.impl;

import com.practice.spring_mini_project_01_group01.exception.NotFoundException;
import com.practice.spring_mini_project_01_group01.model.Comment;
import com.practice.spring_mini_project_01_group01.model.dto.commet.CommentRequest;
import com.practice.spring_mini_project_01_group01.model.dto.commet.CommentResponse;
import com.practice.spring_mini_project_01_group01.repository.CommentRepository;
import com.practice.spring_mini_project_01_group01.repository.UserRepository;
import com.practice.spring_mini_project_01_group01.service.CommentService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  @Override
  public CommentResponse getCommentById(Long commentId) {

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new NotFoundException("Comment Id not found"));
    return comment.toResponse();
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
