package com.electronic.store.services.impl;

import com.electronic.store.dtos.CreateOrderRequest;
import com.electronic.store.dtos.OrderDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.entites.*;
import com.electronic.store.exceptions.BadApiRequest;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.AppConstants;
import com.electronic.store.helper.Helper;
import com.electronic.store.repositories.CartRepository;
import com.electronic.store.repositories.OrderRespository;
import com.electronic.store.repositories.UserRepository;
import com.electronic.store.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class orderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRespository orderRespository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;
    private Order order;


    /**
     * @param orderDto
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This method is used for create Order
     */
    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        log.info("Initiated Dao Call For createOrder ");
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();
//fetch user
        log.info("Request to find user with userId:{}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", AppConstants.USER_NOT_FOUND));

        //fetch cart
        log.info("Request to find cart with cartId:{}", cartId);
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("user", AppConstants.CART_NOT_FOUND));


        List<CartItem> cartItems = cart.getItems();
        if (cartItems.size() <= 0) {
            throw new BadApiRequest("Invalid number of items in cart ");
        }

        Order order = Order.builder().billingName(orderDto.getBillingName()).billingPhone(orderDto.getBillingPhone()).billingAddress(orderDto.getBillingAddress()).orderDate(new Date()).deliveredDate(null).paymentStatus(orderDto.getPaymentStatus()).orderStatus(orderDto.getOrderStatus()).orderId(UUID.randomUUID().toString()).user(user).build();

        //orderItems amount
        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {

            //CartItem->OrderItem


            OrderItem orderItem = OrderItem.builder().quantity(cartItem.getQuantity()).product(cartItem.getProduct()).product(cartItem.getProduct()).totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscounted_price()).order(order).build();

            order.setOrderAmount(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        //
        cart.getItems().clear();
        cartRepository.save(cart);

        Order saveOrder = orderRespository.save(order);
        log.info("Completed Dao Call For createOrder ");
        return modelMapper.map(saveOrder, OrderDto.class);
    }

    /**
     * @param orderId
     * @author Shubham Dhokchaule
     * @apiNote This method is used for remove order
     */
    @Override
    public void removeOrder(String orderId) {
        log.info("Initiated Dao Call For Remove Order with orderId:{}", orderId);
        Order order1 = orderRespository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("user", AppConstants.ORDER_NOT_FOUND));
        orderRespository.delete(order1);
        log.info("Completed Dao Call For Remove Order with orderId:{}", orderId);
    }

    /**
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This method is used for get orders of user
     */
    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        log.info("Initiated Dao Call For get Order of user with userId:{}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", AppConstants.USER_NOT_FOUND));
        List<Order> orders = orderRespository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        log.info("Completed Dao Call For get Order of user with userId:{}", userId);
        return orderDtos;
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This method is used for get all orders
     */
    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiated Dao Call For get all Orders ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = orderRespository.findAll(pageable);
        log.info("COMPLETED Dao Call For get all Orders ");
        return Helper.getPageableResponse(page, OrderDto.class);
    }
}
