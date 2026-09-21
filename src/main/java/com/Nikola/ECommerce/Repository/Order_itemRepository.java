package com.Nikola.ECommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Nikola.ECommerce.model.Order_item;

@Repository
public interface Order_itemRepository extends JpaRepository<Order_item, Integer> {

	

}
