package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostRespone;
import com.springboot.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.blog.utils.AppConstants.*;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name="CRUD REST APIs for Post resource"
)
public class PostController {
    private PostService postService;
@Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post rest api
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
   @Operation(
           summary = "Create Post REST API",
           description = "Create Post REST API is used to save post into database"
   )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<PostDto>createPost(@Valid @RequestBody PostDto postDto){
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Get All Post REST API",
            description = "Get all Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 201 OK"
    )
    @GetMapping
    public PostRespone getAllPosts(@RequestParam(value="pageNo",defaultValue = DEFAULT_PAGE_NUMBER,required = false)int pageNo,
                                   @RequestParam(value ="pageSize",defaultValue = DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                   @RequestParam(value = "sortBy",defaultValue = DEFAULT_SORT_BY,required = false)String sortBy,
                                   @RequestParam(value="sortDir",defaultValue = DEFAULT_SORT_DIRECTION,required = false)String sortDir){
    return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post by Id REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Ok"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable(name="id") long id){
    return ResponseEntity.ok(postService.getPostById(id));
    }
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    //update post by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDto>updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name="id") long id){
    PostDto postRespone= postService.updatePost(postDto,id);
    return new ResponseEntity<>(postRespone,HttpStatus.OK);
    }
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable(name="id") Long id){
    postService.deletePost(id);
    return new ResponseEntity<>("post entity delteed success",HttpStatus.OK);
    }
    @GetMapping("/category/{id}")
    @Operation(
            summary = "Get  Post  By Category REST API",
            description = "Get Post By Category  REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    //build get Post by Category REST API
    public ResponseEntity<List<PostDto>>getPostByCategory(@PathVariable(value = "id") Long categoryId){
    List<PostDto> posts=postService.getPostsByCategory(categoryId);
    return ResponseEntity.ok(posts);
    }
}
