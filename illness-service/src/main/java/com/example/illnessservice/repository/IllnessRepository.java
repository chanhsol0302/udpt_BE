package com.example.illnessservice.repository;

import com.example.illnessservice.model.Illness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface IllnessRepository extends JpaRepository<Illness, UUID> {
	List<Illness> findByNameContainingIgnoreCase(String name);
}