package com.springboot.blog.controller;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name="CRUD REST APIs for Category resource"
)
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //build add category rest api
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto>addCategory( @RequestBody CategoryDto categoryDto){
      CategoryDto saved=  categoryService.addCategory(categoryDto);
      return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto>getCategory(@PathVariable(value="id") Long categoryId){
        CategoryDto cd=categoryService.getCategory(categoryId);
        return ResponseEntity.ok(cd);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>>getAllCateogories(){
        return ResponseEntity.ok(categoryService.getAllCateogries());
    }
@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<CategoryDto>updateCategory(@RequestBody CategoryDto categoryDto,
                                                     @PathVariable(value = "id") Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto,categoryId));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")

    public ResponseEntity<String>deleteCategory(@PathVariable(value = "id")Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category dleeted succesffuly");
    }
}
