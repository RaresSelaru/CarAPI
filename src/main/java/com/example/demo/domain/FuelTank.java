package com.example.demo.domain;

import com.example.demo.domain.entity.FuelTankEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FuelTank {

    private double tankCapacity;
    private double fuelInside;
    private double idleConsumption;
    private double drivingConsumption;
    private double consumption;

    public FuelTank() {
    }

    public FuelTank(double tankCapacity,double fuelInside, double idleConsumption, double drivingConsumption) {
        this.tankCapacity = tankCapacity;
        this.fuelInside = fuelInside;
        this.idleConsumption = idleConsumption;
        this.drivingConsumption = drivingConsumption;
    }

    public FuelTank(FuelTankEntity fuelTankEntity) {
        if (fuelTankEntity != null) {
            this.tankCapacity = fuelTankEntity.getTankCapacity();
            this.fuelInside = fuelTankEntity.getFuelInside();
            this.idleConsumption = fuelTankEntity.getIdleConsumption();
            this.drivingConsumption = fuelTankEntity.getDrivingConsumption();
            this.consumption = fuelTankEntity.getConsumption();
        }
    }


    public double getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(double tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public double getFuelInside() {
        return fuelInside;
    }

    public void setFuelInside(double fuelInside) {
        this.fuelInside = fuelInside;
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
}
