package com.electronic.store.repositories;

import com.electronic.store.entites.Category;
import com.electronic.store.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findByTitleContaining(String title);

}
