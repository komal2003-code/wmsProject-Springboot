package com.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wms.entity.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>{

}
