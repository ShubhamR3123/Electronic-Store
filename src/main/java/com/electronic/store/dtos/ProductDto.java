package com.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {

    private String productId;
    private String title;

    @NotBlank
    private String description;
    private int price;

    private int discounted_price;

    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;

    private String imageName;

}
