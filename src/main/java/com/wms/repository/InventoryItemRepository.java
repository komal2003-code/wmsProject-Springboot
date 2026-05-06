package com.wms.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wms.entity.InventoryItem;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM InventoryItem i WHERE i.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
    Optional<InventoryItem> findByProductId(@Param("productId") Long productId);
}