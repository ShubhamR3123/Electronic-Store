package com.electronic.store.services.impl;

import com.electronic.store.dtos.AddItemToCartRequest;
import com.electronic.store.dtos.CartDto;
import com.electronic.store.entites.Cart;
import com.electronic.store.entites.CartItem;
import com.electronic.store.entites.Product;
import com.electronic.store.entites.User;
import com.electronic.store.exceptions.BadApiRequest;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.AppConstants;
import com.electronic.store.repositories.CartItemRepository;
import com.electronic.store.repositories.CartRepository;
import com.electronic.store.repositories.ProductRepository;
import com.electronic.store.repositories.UserRepository;
import com.electronic.store.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param userId
     * @param request
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This method is used for addItemsToCart
     */
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        log.info("Initiated Dao Call For addItemsToCart With userId:{}", userId);
        int quantity = request.getQuantity();
        String productId = request.getProductId();
        if (quantity <= 0) {
            throw new BadApiRequest(AppConstants.QUANTITY);
        }
        //fetch product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.P_NOT_FOUND));

        //fetch user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();

        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());

        }
        //perform cart operation
        AtomicBoolean updated = new AtomicBoolean(false);
        List<CartItem> cartItem = cart.getItems();
        List<CartItem> updatedItems = cartItem.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                //item already present
                item.setQuantity(quantity);
                item.setTotalPrice(quantity + product.getPrice());
                updated.set(true);

            }
            return item;
        }).collect(Collectors.toList());
        cart.setItems(updatedItems);

        if (!updated.get()) {
            //create item
            CartItem cartItems = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItems);
        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        log.info("Completed Dao Call For addItemsToCartWith userId:{}", userId);
        return modelMapper.map(updatedCart, CartDto.class);
    }


    /**
     * @param userId
     * @param cartItemId
     * @author Shubham Dhokchaule
     * @apiNote This method is used for removeItemsFromCart
     */
    @Override
    public void removeItemFromCart(String userId, Integer cartItemId) {
        log.info("Initiated Dao Call For removeItemFromCart with userId and cartItemId:{}", userId, cartItemId);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("CartItem not found with cartItemId" + cartItemId));
        cartItemRepository.delete(cartItem);
        log.info("Completed Dao Call For removeItemFromCart with userId and cartItemId:{}", userId, cartItemId);

    }

    /**
     * @param userId
     * @author Shubham Dhokchaule
     * @apiNote This method is used for clearCart
     */
    @Override
    public void clearCart(String userId) {
        log.info("Initiated Dao Call For clearCart with userId:{}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart user not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
        log.info("Completed Dao Call For clearCart with userId:{}", userId);
    }

    /**
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This method is used for cartByUser
     */
    @Override
    public CartDto cartByUser(String userId) {
        log.info("Initiated Dao Call For cartByUser with userId:{}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + user));
        log.info("Completed Dao Call For cartByUser:{}", userId);
        return modelMapper.map(cart, CartDto.class);

    }


}
