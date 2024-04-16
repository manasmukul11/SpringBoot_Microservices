package com.MicroServicesproject.OrderServices.repository;


import com.MicroServicesproject.OrderServices.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
