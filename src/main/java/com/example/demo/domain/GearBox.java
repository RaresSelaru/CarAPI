package com.example.demo.domain;

import com.example.demo.domain.entity.GearBoxEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

public class GearBox {

    private int nrGears;
    private int currentGear;
    private int neutral = 0;
    private int reverse = -1;

    public GearBox() {
    }

    public GearBox(int nrGears) {
        this.nrGears = nrGears;
    }

    public GearBox(GearBoxEntity gearBoxEntity) {
        if (gearBoxEntity != null) {
            this.nrGears = gearBoxEntity.getNrGears();
            this.currentGear = gearBoxEntity.getCurrentGear();
        }
    }

    public int getNrGears() {
        return nrGears;
    }

    public void setNrGears(int nrGears) {
        this.nrGears = nrGears;
    }

    public int getCurrentGear() {
        return currentGear;
    }

    public void setCurrentGear(int currentGear) {
        this.currentGear = currentGear;
    }

    public int getNeutral() {
        return neutral;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

    public int getReverse() {
        return reverse;
    }

    public void setReverse(int reverse) {
        this.reverse = reverse;
    }
}
