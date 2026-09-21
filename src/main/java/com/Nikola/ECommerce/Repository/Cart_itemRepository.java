package com.Nikola.ECommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Nikola.ECommerce.model.Cart_item;

@Repository
public interface Cart_itemRepository extends JpaRepository<Cart_item, Integer> {

	List<Cart_item> findAllById(int id);

	List<Cart_item> findAllByCart_id(int id);

	Cart_item findByProduct_idAndCart_id(int productID, int cartID);

	void deleteAllByCart_id(int id);

	

}
