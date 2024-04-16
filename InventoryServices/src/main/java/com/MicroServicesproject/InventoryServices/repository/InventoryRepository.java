package com.MicroServicesproject.InventoryServices.repository;

import com.MicroServicesproject.InventoryServices.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkucode();
}
