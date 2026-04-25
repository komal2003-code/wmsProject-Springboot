package com.wms.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wms.entity.InventoryItem;
import com.wms.entity.Product;
//import com.wms.entity.StorageBin;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
	Optional<InventoryItem> findByProduct(Product product);
}
