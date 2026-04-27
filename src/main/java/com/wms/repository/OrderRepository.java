package com.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wms.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
