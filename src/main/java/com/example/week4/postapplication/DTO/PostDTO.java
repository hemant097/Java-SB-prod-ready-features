package com.example.week4.postapplication.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long postId;

    @NotBlank
    @Size(min=2,max = 40, message="Title should have 2-40 characters")
    private String title;

    @NotBlank
    private String description;

}
