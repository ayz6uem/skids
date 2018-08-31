package com.ybyc.skids.car.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class BaseTbox {

    @JsonIgnore
    protected Car car;

    public boolean connected;

    public BaseTbox(Car car) {
        this.car = car;
    }

    public abstract boolean on();

    public abstract boolean off();

    public void pushStatus(){
        car.update();
    }
}
