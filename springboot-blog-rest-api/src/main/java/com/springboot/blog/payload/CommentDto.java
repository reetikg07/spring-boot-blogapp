package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    @NotEmpty(message = "email shold not be null or empty")
    @Email
    private String email;
    @NotEmpty(message = "Commenet bd=ody should not be emtpy")
    @Size(min=10)
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Name should not be null or empty") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name should not be null or empty") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "email shold not be null or empty") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "email shold not be null or empty") @Email String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Commenet bd=ody should not be emtpy") @Size(min = 10) String getBody() {
        return body;
    }

    public void setBody(@NotEmpty(message = "Commenet bd=ody should not be emtpy") @Size(min = 10) String body) {
        this.body = body;
    }
}
