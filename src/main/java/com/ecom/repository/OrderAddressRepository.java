package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.model.OrderAddress;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Integer>{
	
	@Query(value = "SELECT * FROM order_address o WHERE o.user_id = :userId ORDER BY o.id DESC LIMIT 1", nativeQuery = true)
	 OrderAddress findLastOrderAddress(@Param("userId") Integer userId);

}
