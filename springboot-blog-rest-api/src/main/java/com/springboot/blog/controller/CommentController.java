package com.springboot.blog.controller;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@Tag(
        name="CRUD REST APIs for Comment resource"
)
public class CommentController {
    private CommentService commentService;
@Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto>createComment(@PathVariable("postId")long postId,
                                                   @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);

    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable(value = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>getCommentById(@PathVariable(value = "postId") long postId,
        @PathVariable(value = "commentId") long commentId){
        CommentDto cd=commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(cd,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>updateComment(@PathVariable(value = "postId") Long postId,
                                                   @PathVariable(value = "id") Long commentId,
                                                 @Valid  @RequestBody CommentDto commentDto){
    CommentDto cd=commentService.updateCommentById(postId,commentId,commentDto);
    return new ResponseEntity<>(cd,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String>deleteComment( @PathVariable(value = "postId") long postId,
                                                @PathVariable(value = "commentId") long commentId){
    commentService.deleteComment(postId,commentId);
    return new ResponseEntity<>("Comment Deleted succesfuly",HttpStatus.OK);

    }
}
