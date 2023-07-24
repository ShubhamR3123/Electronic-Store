package com.electronic.store.repositories;

import com.electronic.store.entites.Order;
import com.electronic.store.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRespository extends JpaRepository<Order ,String> {

    List<Order>findByUser(User user);
}
