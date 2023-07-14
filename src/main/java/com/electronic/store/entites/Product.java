package com.electronic.store.entites;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    private String productId;
    private String title;

    @Column(length = 10000)
    private String description;
    private int price;
    private int discounted_price;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;

    private String imageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
    private Category category;
}
