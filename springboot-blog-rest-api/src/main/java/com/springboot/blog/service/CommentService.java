package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(long postId,long commentId);

    CommentDto updateCommentById(long postId,long commentId,CommentDto commentDto);

    void deleteComment(long postId,long commentId);
}
