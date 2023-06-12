package com.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private String categoryId;
    @NotBlank
    @Min(value = 4, message = "Title must be minimum of 4 characters")
    private String title;
    @NotBlank
    @Min(value = 10, message = "Description Must be Minimun of 10characters")
    private String description;
    @NotBlank
    private String coverImage;
}
