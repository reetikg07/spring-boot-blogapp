package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFound;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;
@Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment c=mapToEnitity(commentDto);
        Post p=postRepository.findById(postId).
                orElseThrow(()-> new ResourceNotFound("Post","id",postId));
        c.setPost(p);
        Comment newC=commentRepository.save(c);
        return mapToDto(newC);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment>comments=commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post p=postRepository.findById(postId).
                orElseThrow(()-> new ResourceNotFound("Post","id",postId));
        Comment c=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFound("Comment","id",commentId));

        //if any comment belongs to the purticular post or not
        if(!c.getPost().getId().equals(p.getId())){
           throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to this post");
        }
        return mapToDto(c);
    }

    @Override
    public CommentDto updateCommentById(long postId,long commentId,CommentDto commentDto) {
        Post p=postRepository.findById(postId).
                orElseThrow(()-> new ResourceNotFound("Post","id",postId));
        Comment c=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFound("Comment","id",commentId));
        if(!c.getPost().getId().equals(p.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to this post");
        }

        c.setName(commentDto.getName());
        c.setBody(commentDto.getBody());
        c.setEmail(commentDto.getEmail());
     Comment cnew=   commentRepository.save(c);
        return mapToDto(cnew);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post p=postRepository.findById(postId).
                orElseThrow(()-> new ResourceNotFound("Post","id",postId));
        Comment c=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFound("Comment","id",commentId));
        if(!c.getPost().getId().equals(p.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to this post");
        }
        commentRepository.delete(c);
    }


    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto=mapper.map(comment,CommentDto.class);
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setName(comment.getName());
        return commentDto;
    }
    private Comment mapToEnitity(CommentDto commentDto){
        Comment c=mapper.map(commentDto,Comment.class);
//        c.setId(commentDto.getId());
//        c.setName(commentDto.getName());
//        c.setBody(commentDto.getBody());
//        c.setEmail(commentDto.getEmail());
        return c;
    }
}
