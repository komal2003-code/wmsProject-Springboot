package com.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wms.entity.Warehouse;
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
