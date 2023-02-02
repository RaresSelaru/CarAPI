package com.example.demo.service;

import com.example.demo.domain.entity.CarEntity;
import com.example.demo.domain.entity.FuelTankEntity;
import com.example.demo.repository.FuelTankRepository;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class FuelTankService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final FuelTankRepository fuelTankRepository;


    public FuelTankService(FuelTankRepository fuelTankRepository) {
        this.fuelTankRepository = fuelTankRepository;
    }


    private void fuelWarning(Long carId, double consumption) {
        Optional<FuelTankEntity> fuelTankEntityOptional = fuelTankRepository.findById(carId);
        if (fuelTankEntityOptional.isPresent()) {
            FuelTankEntity fuelTankEntity = fuelTankEntityOptional.get();
            if (fuelTankEntity.getFuelInside() - consumption <= 0) {
                log.info("You ran out of fuel !");
                fuelTankEntity.setFuelInside(0);

                stopConsumingFuel(carId);
                fuelTankRepository.save(fuelTankEntity);
            } else if (fuelTankEntity.getFuelInside() - consumption <= 15 ) {
                log.info("You are running out of fuel");
                log.info("Remaining fuel: " + fuelTankEntity.getFuelInside());
                fuelTankRepository.save(fuelTankEntity);
            }
        }
    }

    public void refill(Long carId, double quantity) {
        Optional<FuelTankEntity> fuelTankEntityOptional = fuelTankRepository.findById(carId);
        if (fuelTankEntityOptional.isPresent()) {
            FuelTankEntity fuelTankEntity = fuelTankEntityOptional.get();
            while (true) {
                if ((quantity == fuelTankEntity.getTankCapacity() || quantity <= fuelTankEntity.getTankCapacity()) &&
                        (fuelTankEntity.getFuelInside() + quantity) <= fuelTankEntity.getTankCapacity()) {
                    fuelTankEntity.setFuelInside(fuelTankEntity.getFuelInside() + quantity);
                    log.info("The fuel inside is: " + fuelTankEntity.getFuelInside() + " liters");
                    fuelTankRepository.save(fuelTankEntity);
                    break;
                }else if(fuelTankEntity.getFuelInside() == fuelTankEntity.getTankCapacity()) {
                    log.info("Fueltank is full");
                    break;
                } else if (quantity > fuelTankEntity.getTankCapacity() || (fuelTankEntity.getFuelInside() + quantity) > fuelTankEntity.getTankCapacity()) {
                    log.info("The amount of fuel you want to add is greater than the capacity of your tank");
                    log.info("You can only add " + (fuelTankEntity.getTankCapacity() - fuelTankEntity.getFuelInside()) + " more liters, or less");
                    break;


                }
            }
        }
    }


    private void consume(Long carId, double consumption) {
        Optional<FuelTankEntity> fuelTankEntityOptional = fuelTankRepository.findById(carId);
        if (fuelTankEntityOptional.isPresent()) {
            FuelTankEntity fuelTankEntity = fuelTankEntityOptional.get();
            fuelTankEntity.setFuelInside(fuelTankEntity.getFuelInside() - consumption);
            fuelWarning(carId,consumption);
            fuelTankEntity.setConsumption(consumption);
            fuelTankRepository.save(fuelTankEntity);
        }
    }


    public void consumeFuel(Long carId, double consumption) {
        Optional<FuelTankEntity> fuelTankEntityOptional = fuelTankRepository.findById(carId);
        if (fuelTankEntityOptional.isPresent()) {
            FuelTankEntity fuelTankEntity = fuelTankEntityOptional.get();
            scheduler.scheduleAtFixedRate(() -> consume(carId,consumption), 0, 1, TimeUnit.MINUTES);
            fuelTankEntity.setConsumptionState(true);
            fuelTankEntity.setConsumption(consumption);
            fuelTankRepository.save(fuelTankEntity);
        }
    }


    public void stopConsumingFuel(Long carId) {
        Optional<FuelTankEntity> fuelTankEntityOptional = fuelTankRepository.findById(carId);
        if (fuelTankEntityOptional.isPresent()) {
            FuelTankEntity fuelTankEntity = fuelTankEntityOptional.get();
            scheduler.shutdown();
            fuelTankEntity.setConsumptionState(false);
            fuelTankEntity.setConsumption(0);
            fuelTankRepository.save(fuelTankEntity);
        }
    }

    public void checkFuelInside(Long carId) {
        Optional<FuelTankEntity> fuelTankEntityOptional = fuelTankRepository.findById(carId);
        if (fuelTankEntityOptional.isPresent()) {
            FuelTankEntity fuelTankEntity = fuelTankEntityOptional.get();
            log.info("You have: " + fuelTankEntity.getFuelInside() + " liters left");
        }
    }

    public void checkConsumption(Long carId) {
        Optional<FuelTankEntity> fuelTankEntityOptional = fuelTankRepository.findById(carId);
        if (fuelTankEntityOptional.isPresent()) {
            FuelTankEntity fuelTankEntity = fuelTankEntityOptional.get();
        log.info("Current consumption is: " + fuelTankEntity.getConsumption());
        }

    }
}
