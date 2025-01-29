package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFound;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl  implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category=modelMapper.map(categoryDto,Category.class);
        Category saved=categoryRepository.save(category);
        return modelMapper.map(saved,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category c=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFound("Category","id",categoryId));
        return modelMapper.map(c,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCateogries() {
        List<Category>categories=categoryRepository.findAll();

        return categories.stream().map((category)->modelMapper.map(category,CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category c=categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFound("Category","id",categoryId));
        c.setName(categoryDto.getName());
        c.setDescription(categoryDto.getDescription());
        c.setId(categoryId);
      Category c1=  categoryRepository.save(c);
      return modelMapper.map(c1,CategoryDto.class);


    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category c=categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFound("Category","id",categoryId));
        categoryRepository.delete(c);
    }
}
