package com.example.demo.repository;

import com.example.demo.domain.FuelTank;
import com.example.demo.domain.entity.FuelTankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FuelTankRepository extends JpaRepository<FuelTankEntity, Long> {

}
