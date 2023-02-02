package com.example.demo.domain.entity;

import com.example.demo.domain.GearBox;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "gearbox")
public class GearBoxEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private int nrGears;
    private int currentGear = 0;

    public GearBoxEntity() {
    }
    public GearBoxEntity(int nrGears) {
        this.nrGears = nrGears;
    }
    public GearBoxEntity(GearBox gearBox) {
        this.nrGears = gearBox.getNrGears();
        this.currentGear = gearBox.getCurrentGear();
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


}
