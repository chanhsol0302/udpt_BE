package com.example.drugInventoryservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "medicines")
@Data
public class Medicine {
	@Id
    @GeneratedValue
    private UUID id;

    @Column(name = "code", unique = true, length = 50)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "price", nullable = false)
    private float price;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}