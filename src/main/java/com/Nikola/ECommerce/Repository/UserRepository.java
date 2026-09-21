package com.Nikola.ECommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Nikola.ECommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
