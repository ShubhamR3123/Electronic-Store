package com.electronic.store.entites;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    private int quantity;

    private int totalPrice;

    @OneToOne
   // @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
   // @JoinColumn(name = "order_id")
    private Order order;
}
