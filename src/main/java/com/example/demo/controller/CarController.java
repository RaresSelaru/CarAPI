package com.example.demo.controller;

import com.example.demo.domain.Car;
import com.example.demo.domain.entity.CarEntity;
import com.example.demo.service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getCars() {
        return carService.getCars();
    }
    @GetMapping(path = "{carId}")
    public Car getCar(@PathVariable Long carId) {
        return carService.getCar(carId);
    }
    @PostMapping
    public void createCar(@RequestBody Car car) {
        carService.createCar(car);
    }
    @PutMapping(path = "{carId}")
    public void updateCar(@PathVariable Long carId, CarEntity carEntity) {
        carService.updateCar(carId,carEntity);
    }
    @DeleteMapping(path = "{carId}")
    public void deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
    }
    @PostMapping(path = "/startengine/{carId}")
    public void startCarEngine(@PathVariable Long carId) {
        carService.startEngine(carId);
    }
    @PostMapping(path = "/stopengine/{carId}")
    public void stopCarEngine(@PathVariable Long carId) {
        carService.stopEngine(carId);
    }
    @PostMapping(path = "/carcondition/{carId}")
    public void checkCarCondition(@PathVariable Long carId) {
        carService.checkCarCondition(carId);
    }
    @PostMapping(path = "/drive/{carId}")
    public void driveCar(@PathVariable Long carId) {
        carService.drive(carId);
    }
    @PostMapping(path = "/stopdriving/{carId}")
    public void stopDrivingCar(@PathVariable Long carId) {
        carService.stopDriving(carId);
    }
    @PostMapping(path = "/shiftgearup/{carId}")
    public void shiftGearUp(@PathVariable Long carId) {
        carService.shiftGearUp(carId);
    }
    @PostMapping(path = "/shiftgeardown/{carId}")
    public void shiftGearDown(@PathVariable Long carId) {
        carService.shiftGearDown(carId);
    }
    @PostMapping(path = "/putinneutral/{carId}")
    public void putInNeutral(@PathVariable Long carId) {
        carService.putInNeutral(carId);
    }
    @PostMapping(path = "/putinreverse/{carId}")
    public void putInReverse(@PathVariable Long carId) {
        carService.putInReverse(carId);
    }
    @PostMapping(path = "/refill/{carId}")
    public void refill(@PathVariable Long carId, @RequestParam double quantity) {
        carService.refill(carId,quantity);
    }
}
