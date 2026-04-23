package com.wms.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wms.entity.StorageBin;
public interface StorageBinRepository extends JpaRepository<StorageBin, Long> {
	Optional<StorageBin> findFirstByCapacityGreaterThanEqual(int quantity);
}
