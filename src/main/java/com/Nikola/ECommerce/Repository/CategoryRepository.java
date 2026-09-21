package com.Nikola.ECommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Nikola.ECommerce.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
