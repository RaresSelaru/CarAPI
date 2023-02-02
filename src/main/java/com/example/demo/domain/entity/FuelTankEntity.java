package com.example.demo.domain.entity;

import com.example.demo.domain.FuelTank;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "fueltank")
public class FuelTankEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private double tankCapacity;
    private double fuelInside;
    private double idleConsumption;
    private double drivingConsumption;

    private double consumption;
    @Transient
    private boolean consumptionState;


    public FuelTankEntity() {
    }

    public FuelTankEntity(double tankCapacity, double fuelInside, double idleConsumption, double drivingConsumption) {
        this.tankCapacity = tankCapacity;
        this.fuelInside = fuelInside;
        this.idleConsumption = idleConsumption;
        this.drivingConsumption = drivingConsumption;
    }
    public FuelTankEntity(FuelTank fuelTank) {
        this.tankCapacity = fuelTank.getTankCapacity();
        this.fuelInside = fuelTank.getFuelInside();
        this.idleConsumption = fuelTank.getIdleConsumption();
        this.drivingConsumption = fuelTank.getDrivingConsumption();
        this.consumption = fuelTank.getConsumption();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(double tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public double getFuelInside() {
        BigDecimal bigDecimalFuelInside = new BigDecimal(Double.toString(fuelInside));
        bigDecimalFuelInside.setScale(2,RoundingMode.HALF_UP);
        double fuelInside = bigDecimalFuelInside.doubleValue();
        return fuelInside;
    }

    public void setFuelInside(double fuelInside) {
        BigDecimal bigDecimalFuelInside = new BigDecimal(fuelInside);
        bigDecimalFuelInside = bigDecimalFuelInside.setScale(2, RoundingMode.HALF_UP);
        this.fuelInside = bigDecimalFuelInside.doubleValue();
    }

    public double getIdleConsumption() {
        return idleConsumption;
    }

    public void setIdleConsumption(double idleConsumption) {
        this.idleConsumption = idleConsumption;
    }

    public double getDrivingConsumption() {
        return drivingConsumption;
    }

    public void setDrivingConsumption(double drivingConsumption) {
        this.drivingConsumption = drivingConsumption;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public boolean getConsumptionState() {
        return consumptionState;
    }

    public void setConsumptionState(boolean consumptionState) {
        this.consumptionState = consumptionState;
    }
}
