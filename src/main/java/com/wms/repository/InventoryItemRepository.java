package com.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wms.entity.InventoryItem;
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
}
