package com.electronic.store.dtos;

import com.electronic.store.entites.OrderItem;
import com.electronic.store.entites.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {



    private String orderId;
    private  String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate=new Date();
    private Date deliveredDate;
    private List<OrderItemDto> orderItems=new ArrayList<>();

}
