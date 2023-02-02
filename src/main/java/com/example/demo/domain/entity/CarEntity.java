package com.example.demo.domain.entity;

import com.example.demo.domain.Car;
import com.example.demo.domain.FuelTank;
import com.example.demo.domain.GearBox;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long carId;
    private String brand;
    private String model;
    private String color;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fueltank_id", referencedColumnName = "id")
    private FuelTankEntity fuelTankEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gearbox_id", referencedColumnName = "id")
    private GearBoxEntity gearBoxEntity;
    private boolean carMoving;
    private boolean carReversing;

    private boolean engineState;
    @Transient
    private boolean handBrake;


    public CarEntity(String brand,String model, String color, FuelTankEntity fuelTankEntity, GearBoxEntity gearBoxEntity) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.fuelTankEntity = fuelTankEntity;
        this.gearBoxEntity = gearBoxEntity;
    }
    public CarEntity() {

    }
    public CarEntity(Car car) {
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.color = car.getColor();
        this.engineState = car.getEngineState();
        this.fuelTankEntity = new FuelTankEntity(car.getFuelTank());
        this.gearBoxEntity = new GearBoxEntity(car.getGearBox());
        this.carMoving = car.isCarMoving();
        this.carReversing = car.isCarReversing();
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    public FuelTankEntity getFuelTankEntity() {
        return fuelTankEntity;
    }

    public void setFuelTankEntity(FuelTankEntity fuelTankEntity) {
        this.fuelTankEntity = fuelTankEntity;
    }

    public GearBoxEntity getGearBoxEntity() {
        return gearBoxEntity;
    }

    public void setGearBoxEntity(GearBoxEntity gearBoxEntity) {
        this.gearBoxEntity = gearBoxEntity;
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

    public boolean getHandBrake() {
        return handBrake;
    }

    public void setHandBrake(boolean handBrake) {
        this.handBrake = handBrake;
    }
}
