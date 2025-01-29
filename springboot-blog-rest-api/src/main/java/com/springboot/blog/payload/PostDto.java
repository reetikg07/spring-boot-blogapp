package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;
@Data
@Schema(
        description = "Post Dto Model Information"
)
public class PostDto {

    private long id;
     @NotEmpty
     @Size(min=2 ,message = "Post title should have atleast 2 charachters")
   @Schema(
           description = "Blog Post Tile"
   )
    private String title;
    @NotEmpty
    @Size(min=10 ,message = "Post description should have atleast 2 charachters")
    @Schema(
            description = "Blog post description"
    )
    private String description;
    @NotEmpty
    @Schema(
            description = "Blog post content"
    )
    private String content;
    @Schema(
            description = "Blog post comments"
    )
    private Set<CommentDto> comments;

    private Long categoryId;

 public long getId() {
  return id;
 }

 public void setId(long id) {
  this.id = id;
 }

 public @NotEmpty @Size(min = 2, message = "Post title should have atleast 2 charachters") String getTitle() {
  return title;
 }

 public void setTitle(@NotEmpty @Size(min = 2, message = "Post title should have atleast 2 charachters") String title) {
  this.title = title;
 }

 public @NotEmpty String getContent() {
  return content;
 }

 public void setContent(@NotEmpty String content) {
  this.content = content;
 }

 public @NotEmpty @Size(min = 10, message = "Post description should have atleast 2 charachters") String getDescription() {
  return description;
 }

 public void setDescription(@NotEmpty @Size(min = 10, message = "Post description should have atleast 2 charachters") String description) {
  this.description = description;
 }

 public Set<CommentDto> getComments() {
  return comments;
 }

 public void setComments(Set<CommentDto> comments) {
  this.comments = comments;
 }

 public Long getCategoryId() {
  return categoryId;
 }

 public void setCategoryId(Long categoryId) {
  this.categoryId = categoryId;
 }
}
