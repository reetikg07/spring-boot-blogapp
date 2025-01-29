package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFound;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostRespone;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;
    private CategoryRepository categoryRepository;
@Autowired
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper=mapper;
        this.categoryRepository=categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
    //convert Dto to entity
Category c= categoryRepository.findById(postDto.getCategoryId())
         .orElseThrow(()->new ResourceNotFound("Category","id",postDto.getCategoryId()));


          Post post=mapToPost(postDto);
          post.setCategory(c) ;
//        post.setTitle(postDto.getContent());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        Post newPost=postRepository.save(post);

        PostDto postResponse=mapToDto(newPost);
        return  postResponse;
    }

    @Override
    public PostRespone getAllPosts(int pageNo, int pageSize,String sortyBy,String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortyBy).ascending():Sort.by(sortyBy).descending();
    // Create a Pageable object
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        // Fetch a page of posts from the repository
        Page<Post> posts = postRepository.findAll(pageable);
        // Extract the content from the Page object
        List<Post> listOfPosts = posts.getContent();
        // Map each Post to a PostDto
        List<PostDto>content= listOfPosts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        PostRespone postRespone=new PostRespone();
        postRespone.setContent(content);
        postRespone.setPageNo(posts.getNumber());
        postRespone.setPageSize(posts.getSize());
        postRespone.setTotalElements(posts.getTotalElements());
        postRespone.setTotalPages(posts.getTotalPages());
        postRespone.setLast(posts.isLast());

        return postRespone;
    }


    @Override
    public PostDto getPostById(Long id) {
    Post p=postRepository.findById(id).orElseThrow(()->new ResourceNotFound("Post","id",id));
        return mapToDto(p);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post p=postRepository.findById(id).orElseThrow(()->new ResourceNotFound("Post","id",id));
      Category c= categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->new ResourceNotFound("Category","id", postDto.getCategoryId()));

        p.setTitle(postDto.getTitle());
        p.setDescription(postDto.getDescription());
        p.setContent(postDto.getContent());
        p.setCategory(c);
        Post updatePost=postRepository.save(p);
        return mapToDto(updatePost);


    }

    @Override
    public void deletePost(long id) {
        Post p=postRepository.findById(id).orElseThrow(()->new ResourceNotFound("Post","id",id));
        postRepository.delete(p);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
   Category category= categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFound("Category","id",categoryId));
     List<Post> posts=postRepository.findByCategory_Id(categoryId);

        return posts.stream().map((post)->mapToDto(post)).collect(Collectors.toList());
    }

    //convert Entity into Dto
    private PostDto mapToDto(Post post){

    PostDto postDto=mapper.map(post,PostDto.class);
//    postDto.setId(post.getId());
//    postDto.setTitle(post.getTitle());
//    postDto.setDescription(post.getDescription());
//    postDto.setContent(post.getContent());
    return postDto;

    }

    private Post mapToPost(PostDto postDto){
        Post post=mapper.map(postDto,Post.class);
//        post.setTitle(postDto.getContent());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;

    }
}
