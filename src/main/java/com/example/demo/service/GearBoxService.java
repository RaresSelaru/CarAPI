package com.example.demo.service;

import com.example.demo.domain.GearBox;
import com.example.demo.domain.entity.FuelTankEntity;
import com.example.demo.domain.entity.GearBoxEntity;
import com.example.demo.repository.GearBoxRepository;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.LongAccumulator;
@Service
@Slf4j
public class GearBoxService {
    private final GearBoxRepository gearBoxRepository;

    public GearBoxService(GearBoxRepository gearBoxRepository) {
        this.gearBoxRepository = gearBoxRepository;
    }



    public void shiftGearUp(Long carId) {
        Optional<GearBoxEntity> gearBoxEntityOptional = gearBoxRepository.findById(carId);
        if (gearBoxEntityOptional.isPresent()) {
            GearBoxEntity gearBoxEntity = gearBoxEntityOptional.get();
            int currentGear = gearBoxEntity.getCurrentGear();
            if (gearBoxEntity.getCurrentGear() < gearBoxEntity.getNrGears() && gearBoxEntity.getCurrentGear() >= 1) {
                gearBoxEntity.setCurrentGear(++currentGear);
                gearBoxRepository.save(gearBoxEntity);
                log.info("Shifted gear to " + gearBoxEntity.getCurrentGear());
            }
        }
    }

    public void shiftGearDown(Long carId) {
        Optional<GearBoxEntity> gearBoxEntityOptional = gearBoxRepository.findById(carId);
        if (gearBoxEntityOptional.isPresent()) {
            GearBoxEntity gearBoxEntity = gearBoxEntityOptional.get();
            int currentGear = gearBoxEntity.getCurrentGear();
            if (gearBoxEntity.getCurrentGear() <= gearBoxEntity.getNrGears()  && gearBoxEntity.getCurrentGear() >= 1) {
                gearBoxEntity.setCurrentGear(--currentGear);
                gearBoxRepository.save(gearBoxEntity);
                log.info("Shifted gear to " + gearBoxEntity.getCurrentGear());
            }
        }
    }

    public void putInNeutral(Long carId) {
        Optional<GearBoxEntity> gearBoxEntityOptional = gearBoxRepository.findById(carId);
        if (gearBoxEntityOptional.isPresent()) {
            GearBoxEntity gearBoxEntity = gearBoxEntityOptional.get();
            if (gearBoxEntity.getCurrentGear() != 0) {
                gearBoxEntity.setCurrentGear(0);
                gearBoxRepository.save(gearBoxEntity);
                log.info("Car is in neutral");
            } else {
                log.info("Car is already in neutral");
            }
        }
    }

    public void putInReverse(Long carId) {
        Optional<GearBoxEntity> gearBoxEntityOptional = gearBoxRepository.findById(carId);
        if (gearBoxEntityOptional.isPresent()) {
            GearBoxEntity gearBoxEntity = gearBoxEntityOptional.get();
            if (gearBoxEntity.getCurrentGear() == -1) {
                log.info("Car is already in reverse");
            } else if (gearBoxEntity.getCurrentGear() != 0) {
                log.info("Car needs to be in neutral first");
            } else {
                gearBoxEntity.setCurrentGear(-1);
                gearBoxRepository.save(gearBoxEntity);
                log.info("Car is in reverse");
            }
        }
    }

    public void checkGearBox(Long carId) {
        Optional<GearBoxEntity> gearBoxEntityOptional = gearBoxRepository.findById(carId);
        if (gearBoxEntityOptional.isPresent()) {
            GearBoxEntity gearBoxEntity = gearBoxEntityOptional.get();
            if (gearBoxEntity.getCurrentGear() == 0) {
                log.info("Gear box is in neutral");
            } else if (gearBoxEntity.getCurrentGear() == -1) {
                log.info("Gear box is in reverse");
            } else {
                String gear = String.valueOf(gearBoxEntity.getCurrentGear());
                log.info ("Gear box is in gear " + gear);
            }
        }
    }
}
