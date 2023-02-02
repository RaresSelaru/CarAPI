package com.example.demo.domain;

import com.example.demo.domain.entity.CarEntity;
import jakarta.persistence.Entity;
import lombok.Data;


public class Car {

    private  Long id;
    private String brand;
    private String model;
    private String color;
    private FuelTank fuelTank;
    private GearBox gearBox;
    private boolean carMoving = false;
    private boolean carReversing = false;
    private boolean engineState = false;
    private boolean handBrake = false;


    public Car(String brand,String model, String color, FuelTank fuelTank, GearBox gearBox) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.fuelTank = fuelTank;
        this.gearBox = gearBox;
    }
    public Car() {

    }
    public Car(CarEntity carEntity) {
        this.id = carEntity.getCarId();
        this.brand = carEntity.getBrand();
        this.model = carEntity.getModel();
        this.color = carEntity.getColor();
        this.engineState = carEntity.getEngineState();
        this.fuelTank = new FuelTank(carEntity.getFuelTankEntity());
        this.gearBox = new GearBox(carEntity.getGearBoxEntity());
        this.carMoving = carEntity.isCarMoving();
        this.carReversing = carEntity.isCarReversing();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public FuelTank getFuelTank() {
        return fuelTank;
    }

    public void setFuelTank(FuelTank fuelTank) {
        this.fuelTank = fuelTank;
    }

    public GearBox getGearBox() {
        return gearBox;
    }

    public void setGearBox(GearBox gearBox) {
        this.gearBox = gearBox;
    }

    public boolean isCarMoving() {
        return carMoving;
    }

    public void setCarMoving(boolean carMoving) {
        this.carMoving = carMoving;
    }

    public boolean isCarReversing() {
        return carReversing;
    }

    public void setCarReversing(boolean carReversing) {
        this.carReversing = carReversing;
    }

    public boolean getEngineState() {
        return engineState;
    }

    public void setEngineState(boolean engineState) {
        this.engineState = engineState;
    }

    public boolean getHandBrakeState() {
        return handBrake;
    }

    public void setHandBrakeState(boolean handBrake) {
        this.handBrake = handBrake;
    }
}
