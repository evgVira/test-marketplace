package com.example.simple_marketplace.repositories;

import com.example.simple_marketplace.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
