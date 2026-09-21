package com.Nikola.ECommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Nikola.ECommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
