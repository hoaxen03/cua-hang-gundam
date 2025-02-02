package com.example.gundamstore.repository;

import com.example.gundamstore.model.Gundam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GundamRepository extends JpaRepository<Gundam, String> {
}
