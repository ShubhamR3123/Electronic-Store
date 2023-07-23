package com.electronic.store.services;

import com.electronic.store.dtos.AddItemToCartRequest;
import com.electronic.store.dtos.CartDto;

public interface CartService {

    CartDto addItemToCart(String userId, AddItemToCartRequest request);


    void removeItemFromCart(String userId, Integer  cartItemId);

    //remove all item from cart
     void clearCart(String userId);

     //cart by user

    CartDto cartByUser(String userId);
}
