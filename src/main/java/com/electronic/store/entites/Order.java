package com.electronic.store.entites;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "orders")
public class Order {


    @Id
    private String orderId;

    //PENDING DISPATCHED DELIVERED
    private  String orderStatus;

    //NOT-PAID ,PAID
    private String paymentStatus;

    private int orderAmount;

    @Column(length = 100)
    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderDate;

    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<OrderItem>orderItems=new ArrayList<>();

}
