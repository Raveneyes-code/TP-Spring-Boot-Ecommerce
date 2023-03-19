package com.course.product.repositories;

import com.course.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findAllByNameLike(String name);
}
