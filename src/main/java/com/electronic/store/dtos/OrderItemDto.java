package com.electronic.store.dtos;

import com.electronic.store.entites.Order;
import com.electronic.store.entites.Product;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemDto {

    private int orderItemId;
    private int quantity;
    private int totalPrice;

    private ProductDto product;


}
