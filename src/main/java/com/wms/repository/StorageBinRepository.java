package com.wms.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.wms.entity.StorageBin;

public interface StorageBinRepository extends JpaRepository<StorageBin, Long> {

    @Query("SELECT b FROM StorageBin b WHERE b.used < b.capacity ORDER BY b.id ASC")
    List<StorageBin> findAvailableBins(); // 🔥 LIST return
}