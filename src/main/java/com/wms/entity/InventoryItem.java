
package com.wms.entity;

import jakarta.persistence.*;
//import lombok.Data;

@Entity
//@Data
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "storage_bin_id")
    private StorageBin storageBin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public StorageBin getStorageBin() {
		return storageBin;
	}

	public void setStorageBin(StorageBin storageBin) {
		this.storageBin = storageBin;
	}
}