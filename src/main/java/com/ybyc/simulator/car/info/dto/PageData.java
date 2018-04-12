package com.ybyc.simulator.car.info.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageData<T> {
    private List<T> records;

}
