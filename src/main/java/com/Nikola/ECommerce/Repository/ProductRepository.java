package com.Nikola.ECommerce.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Nikola.ECommerce.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    List<Product> findAllByNameContainingIgnoreCase(String name);

    List<Product> findAllByNameContainingIgnoreCaseAndPriceBetween(String name,Float minPrice, Float maxPrice);

    List<Product> findAllByNameContainingIgnoreCaseAndPriceGreaterThanEqual(String name,Float minPrice);


    List<Product> findAllByNameContainingIgnoreCaseAndPriceLessThanEqual(String name, Float maxPrice);

    List<Product> findAllByNameContainingIgnoreCaseAndPriceBetween(String name, Float minPrice, Float maxPrice, Sort sort);

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.price>:price
           """)
    List<Product> findProductsExpensiveThan(@Param("price") float price);

    Specification<Product> spec = (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%Monitor%"
            );

}
