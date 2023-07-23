package com.electronic.store.entites;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cart {
    @Id
    private String cartId;
    private Date createdAt;
    @OneToOne
    @JoinColumn(name ="user_Id")
    private User user;
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CartItem> items=new ArrayList<>();
}
