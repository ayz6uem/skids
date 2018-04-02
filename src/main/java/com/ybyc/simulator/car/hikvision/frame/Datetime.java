package com.ybyc.simulator.car.hikvision.frame;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Datetime {
    private byte year;
    private byte month;
    private byte day;
    private byte hour;
    private byte minute;
    private byte second;

    @Override
    public String toString() {
        return "20" + year +"-" + String.format("%02d",month) + "-" + String.format("%02d",day)+ " " + String.format("%02d",hour) + ":"
                + String.format("%02d",minute) +":" + String.format("%02d",second);
    }

    public static Datetime now(){
        Datetime datetime = new Datetime();
        LocalDateTime localDateTime = LocalDateTime.now();
        datetime.year = (byte) localDateTime.getYear();
        datetime.month = (byte)localDateTime.getMonthValue();
        datetime.day = (byte)localDateTime.getDayOfMonth();
        datetime.hour = (byte)localDateTime.getDayOfMonth();
        datetime.minute = (byte)localDateTime.getMinute();
        datetime.second = (byte)localDateTime.getSecond();
        return datetime;
    }
}
