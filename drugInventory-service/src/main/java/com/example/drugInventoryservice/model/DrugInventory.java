package com.example.drugInventoryservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "drug_inventory")
@Data
public class DrugInventory {
	@Id
	private UUID medicineId;
	
	@OneToOne
	@JoinColumn(name = "medicine_id", referencedColumnName = "id")
	@MapsId
	private Medicine medicine;
	

	@Column(name = "stock_quantity", nullable = false)
	private Integer stockQuantity;

	@Column(name = "available_quantity", nullable = false)
	private Integer availableQuantity;
	
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}