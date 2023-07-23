package com.electronic.store.dtos;

import com.electronic.store.entites.CartItem;
import com.electronic.store.entites.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private UserDto userDto;
    private List<CartItemDto> items = new ArrayList<>();
}
