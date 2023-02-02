package com.example.demo.service;

import com.example.demo.domain.Car;
import com.example.demo.domain.entity.CarEntity;
import com.example.demo.domain.entity.FuelTankEntity;
import com.example.demo.domain.entity.GearBoxEntity;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.FuelTankRepository;
import com.example.demo.repository.GearBoxRepository;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarService {

    private final CarRepository carRepository;
    private final FuelTankRepository fuelTankRepository;
    private final GearBoxRepository gearBoxRepository;
    @Autowired
    private FuelTankService fuelTankService;
    @Autowired
    private GearBoxService gearBoxService;

    public CarService(CarRepository carRepository, FuelTankRepository fuelTankRepository, GearBoxRepository gearBoxRepository) {
        this.carRepository = carRepository;
        this.fuelTankRepository = fuelTankRepository;
        this.gearBoxRepository = gearBoxRepository;
    }


    @PreDestroy
    public void resetCarSpecs() {
        List<CarEntity> cars = carRepository.findAll();
        for (CarEntity car : cars) {
            car.setEngineState(false);
            car.setCarReversing(false);
            car.setCarMoving(false);
            FuelTankEntity fuelTankEntity = car.getFuelTankEntity();
            fuelTankEntity.setConsumption(0);
            fuelTankRepository.save(fuelTankEntity);
            GearBoxEntity gearBoxEntity = car.getGearBoxEntity();
            gearBoxEntity.setCurrentGear(0);
            gearBoxRepository.save(gearBoxEntity);
            carRepository.save(car);
        }
    }
    public List<Car> getCars() {
        List<CarEntity> carEntities = carRepository.findAll();
        return carEntities.stream().map(carEntity -> new Car(carEntity)).toList();
    }
    public Car getCar(Long carId) {
        CarEntity carEntity = carRepository.findById(carId).orElse(null);
        if (carEntity != null) {
            return new Car(carEntity);
        }
        return null;
    }
    public void createCar(Car car) {
            CarEntity carEntity = new CarEntity(car);
        carRepository.save(carEntity);
    }
    public void updateCar(Long carId, CarEntity carEntity) {
        CarEntity carToUpdate = carRepository.findById(carId).orElseThrow(
                () -> new IllegalArgumentException(String.format("Car with id %s does not exist", carId)));
        carToUpdate.setBrand(carEntity.getBrand());
        carToUpdate.setModel(carEntity.getModel());
        carToUpdate.setColor(carEntity.getColor());
        carToUpdate.setFuelTankEntity(carEntity.getFuelTankEntity());
        carToUpdate.setGearBoxEntity(carEntity.getGearBoxEntity());

        carRepository.save(carToUpdate);
    }
    public void deleteCar(Long carId) {
        boolean carExists = carRepository.existsById(carId);
        if(!carExists) {
            throw new IllegalStateException(String.format("Car with id %s does not exist",carId));
        }
        carRepository.deleteById(carId);
    }
    public void shiftGearUp(Long carId) {
            gearBoxService.shiftGearUp(carId);
    }
    public void shiftGearDown(Long carId) {
            gearBoxService.shiftGearDown(carId);
    }
    public void putInNeutral(Long carId) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
        if (carEntityOptional.isPresent()) {
            CarEntity carEntity = carEntityOptional.get();
            fuelTankService.consumeFuel(carId,carEntity.getFuelTankEntity().getIdleConsumption());
            gearBoxService.putInNeutral(carId);
        }
    }
    public void putInReverse(Long carId) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
        if (carEntityOptional.isPresent()) {
            CarEntity carEntity = carEntityOptional.get();
            fuelTankService.consumeFuel(carId, carEntity.getFuelTankEntity().getDrivingConsumption());
            gearBoxService.putInReverse(carId);
        }
    }
    public void refill(Long carId, double quantity) {
            fuelTankService.refill(carId, quantity);
    }



    public void startEngine(Long carId) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
        if (carEntityOptional.isPresent()) {
            CarEntity carEntity = carEntityOptional.get();
            if (carEntity.getFuelTankEntity().getFuelInside() <= 0) {
                log.info("You have no fuel");
            } else if (carEntity.getEngineState() == true) {
                log.info("Engine is already on");
            } else {
                carEntity.setEngineState(true);
                carEntity.getFuelTankEntity().setConsumption(carEntity.getFuelTankEntity().getIdleConsumption());
                    fuelTankService.consumeFuel(carId,carEntity.getFuelTankEntity().getIdleConsumption());
                log.info("Engine is on");
                carRepository.save(carEntity);
            }
        }
    }
    public void stopEngine(Long carId) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
        if (carEntityOptional.isPresent()) {
            CarEntity carEntity = carEntityOptional.get();
            if (carEntity.getEngineState() == false) {
                log.info("Engine is already off");
            } else {
                if (carEntity.isCarMoving() == true) {
                    log.info("Stop the car before turning the engine off");
                } else {
                    carEntity.setEngineState(false);
                    fuelTankService.stopConsumingFuel(carId);
                    log.info("Engine is off");
                    carRepository.save(carEntity);
                }
            }
        }
    }


    public void checkCarCondition(Long carId) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
        if (carEntityOptional.isPresent()) {
            CarEntity carEntity = carEntityOptional.get();
            if (carEntity.getEngineState()) {
                log.info("Engine is on");
            } else if (!(carEntity.getEngineState())) {
                log.info("Engine is off");
            }
            if (carEntity.isCarMoving() == true) {
                log.info("Car is currently moving");
            } else {
                log.info("Car is not moving");
            }
            gearBoxService.checkGearBox(carId);
            fuelTankService.checkFuelInside(carId);
            fuelTankService.checkConsumption(carId);
            carRepository.save(carEntity);

        }
    }
    public void drive(Long carId) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
        if (carEntityOptional.isPresent()) {
            CarEntity carEntity = carEntityOptional.get();
            if(carEntity.getFuelTankEntity().getFuelInside() <= 0) {
              log.info("You have no fuel");
            } else if(carEntity.getEngineState() == false) {
                log.info("How do you suppose to drive the car if the engine is turned off? :)");
            } else  if(carEntity.getGearBoxEntity().getCurrentGear() != 1) {
                log.info("Car must be in gear one to move from place");
            } else if(carEntity.getHandBrake() == true) {
                log.info("Release handbrake");
            } else if(carEntity.getGearBoxEntity().getCurrentGear() == -1){
                log.info("Car is reversing");
                carEntity.setCarMoving(true);
                fuelTankService.consumeFuel(carId, carEntity.getFuelTankEntity().getDrivingConsumption());
            } else {
                carEntity.setCarMoving(true);
                carRepository.save(carEntity);
            }
        }
    }
    public void stopDriving(Long carId) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
        if (carEntityOptional.isPresent()) {
            CarEntity carEntity = carEntityOptional.get();
       if(carEntity.isCarMoving() == true) {
           carEntity.setCarMoving(false);
           fuelTankService.consumeFuel(carId,carEntity.getFuelTankEntity().getIdleConsumption());
       }else {
           log.info("The car is not moving");
           carRepository.save(carEntity);
       }
        }
    }
}
