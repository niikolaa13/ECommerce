package com.Nikola.ECommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Nikola.ECommerce.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	List<Orders> findAllByUser_id(int userId);

}
