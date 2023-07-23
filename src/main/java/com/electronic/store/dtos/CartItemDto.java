package com.electronic.store.dtos;

import com.electronic.store.entites.Cart;
import com.electronic.store.entites.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;

    private ProductDto productDto;

    private int quantity;

    private int totalPrice;
}
