package com.ybyc.skids.car.info.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatusInfoDTO implements Serializable {

    /**
     * 车辆编号
     */
    private String carSn;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 车架号
     */
    private String vin;

    /**
     * 车型编码
     */
    private String carModelCode;

    /**
     * 车型名称
     */
    private String carModelName;

    /**
     * 省份区域编码
     */
    private Long provinceId;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 城市外键
     */
    private Long cityId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 租赁类型编码
     */
    private Integer rentTypeCode;

    /**
     * 租赁类型名称
     */
    private String rentTypeName;

    /**
     * 租赁状态编码
     */
    private Integer rentStatusCode;

    /**
     * 租赁状态名称
     */
    private String rentStatusName;

    /**
     * 状态上报时间
     */
    private Long reportTime;

    /**
     * 离线时间
     */
    private Long lostAfter;

    /**
     * 纬度
     */
    private Double lat;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 当前所在城市编码
     */
    private Long nowCityId;
    /**
     * 速度
     */
    private Double speed;
    /**
     * 总里程
     */
    private Double odo;

    /**
     * 剩余电量
     */
    private Double soc;

    /**
     * 充电状态[-1-获取失败，0-未充电，1-充电中，2-充电完成，4-充电故障]
     */
    private Integer charging;

    /**
     * 钥匙[-1-获取失败，0-OFF，1-ON]
     */
    private Integer acc;

    /**
     * 车门总状态[-1获取失败,0-关闭,1-开启]
     */
    private Integer door;

    /**
     * 车窗总状态[-1获取失败,0-关闭,1-开启]
     */
    private Integer window;

    /**
     * 车灯[-1-获取失败 0-关闭 1-开启]
     */
    private Integer light;

    /**
     * 车锁[-1-获取失败 0-关闭 1-开启]
     */
    private Integer lock;

    /**
     * 档位[-1-获取失败 1-P 2-R 3-N 4-D]
     */
    private Integer gear;

    /**
     * 是否允许驾驶[-1获取失败 0-关闭 1-开启]
     */
    private Integer ready;

    /**
     * 移动信号[-1获取失败，0-无，... ，31-满]
     */
    private Integer cellularSignal;

    /**
     * 定位信号[定位信号，-1获取失败，0-无，... ，31-满]
     */
    private Integer locationSignal;

    /**
     * 小电瓶电压，-1获取失败
     */
    private Double voltage;

    /**
     * 动力电压，-1获取失败
     */
    private Double powerVoltage;

    /**
     * 动力电流，-1获取失败
     */
    private Double powerCurrent;

    /**
     * 天线状态，-1获取失败，0-异常，1-正常
     */
    private Integer antenna;

    /**
     * 续航里程
     */
    private Double remainMileage;

    /**
     * 定位状态[-1获取失败，0-未定位，1-已定位]
     */
    private Integer location;

    /**
     * 方位角度，-1获取失败
     */
    private Integer bearing;

    /**
     * 前一个状态[0-故障 1-空闲 2-预约 4-租赁 8-运维 9-亏电]
     */
    private Integer prevStatus;

    /**
     * 当前状态[0-故障 1-空闲 2-预约 4-租赁 8-运维 9-亏电]
     */
    private Integer nowStatus;

    /**
     * 会员卡号，-1无
     */
    private Integer cardNumber;


    /**
     * 是否低电量
     */
    private Boolean lowPower;

    /**
     * 是否在充电
     */
    private Boolean charged;

    /**
     * 是否在线
     */
    private Boolean active;

    /**
     *
     */
    private String machineStatus;

    /**
     *
     */
    private Integer count;
    /**
     * 关联客体
     */
    private String association;

}
