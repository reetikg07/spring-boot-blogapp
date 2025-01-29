package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostRespone;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostRespone getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto,long id);

    void deletePost(long id);

    List<PostDto> getPostsByCategory(Long categoryId);
}
