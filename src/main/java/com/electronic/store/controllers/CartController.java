package com.electronic.store.controllers;

import com.electronic.store.dtos.AddItemToCartRequest;
import com.electronic.store.dtos.CartDto;
import com.electronic.store.helper.ApiResponse;
import com.electronic.store.services.CartService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * @param request
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for addItemsToCart
     */
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemsToCart(@RequestBody AddItemToCartRequest request, @PathVariable String userId) {
        log.info("Initiated Request for add Item to Cart with userId:{}", userId);
        CartDto cartDto = cartService.addItemToCart(userId, request);
        log.info("Completed Request for add Item to Cart with userId:{}", userId);
        return new ResponseEntity(cartDto, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @param itemId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for removeItemsFromCart
     */
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFormCart(@PathVariable String userId, @PathVariable Integer itemId) {
        log.info("Initiated Request for Remove Item to Cart with userId and itemId:{}", userId, itemId);
        cartService.removeItemFromCart(userId, itemId);
        ApiResponse response = ApiResponse.builder().message("Item Removed Successfullyy").success(true).Status(HttpStatus.OK).build();
        log.info("Completed Request for Remoove Item to Cart with userId and itemId:{}", userId, itemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for clearCart
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        log.info("Initiated Request for clear Cart with userId:{}", userId);
        cartService.clearCart(userId);
        ApiResponse response = ApiResponse.builder().message("Cart is Blank").success(true).Status(HttpStatus.OK).build();
        log.info("Completed Request for clear Cart with userId:{}", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for cartByUser
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> cartByUser(@PathVariable String userId) {
        log.info("Initiated Request for get Cart By user with userId:{}", userId);
        CartDto cartDto = cartService.cartByUser(userId);
        log.info("Completed Request for get Cart By user with userId:{}", userId);
        return new ResponseEntity(cartDto, HttpStatus.OK);
    }
}
