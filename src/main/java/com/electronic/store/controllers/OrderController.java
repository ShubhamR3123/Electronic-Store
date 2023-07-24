package com.electronic.store.controllers;

import com.electronic.store.dtos.CreateOrderRequest;
import com.electronic.store.dtos.OrderDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.helper.ApiResponse;
import com.electronic.store.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used forcreate Order
     * @param request
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Initiated Request for create Order");
        OrderDto order = orderService.createOrder(request);
        log.info("Completed Request for create Order");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for remove Order
     * @param orderId
     * @return
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        log.info("Initiated Request for remove Order with orderId:{}", orderId);
        orderService.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().message("Order Remove Successfully").success(true).Status(HttpStatus.OK).build();
        log.info("Completed Request for remove Order with orderId:{}", orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for getOrders of user
     * @param userId
     * @return
     */
    @GetMapping("{userId}")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId) {
        log.info("Initiated Request for get Order of user with userId:{}", userId);
        List<OrderDto> dtoList = orderService.getOrdersOfUser(userId);
        log.info("Completed Request for get Order of user with userId:{}", userId);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote THis Api is Used for get all orders
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/order")
    public ResponseEntity<PageableResponse> getAllOrders(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        log.info("Initiated Request for get all orders");
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for get all orders");
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
