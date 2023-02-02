package com.example.demo.repository;

import com.example.demo.domain.GearBox;
import com.example.demo.domain.entity.GearBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GearBoxRepository extends JpaRepository<GearBoxEntity, Long> {

}
