package com.ybyc.skids.info.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InfoDTO implements Serializable {

    /**
     * 车辆ID
     */
    private Long carId;

    /**
     * 车型ID
     */
    private Integer carModelId;

    /**
     * 车型编码
     */
    private String carModelCode;

    /**
     * 车型名称
     */
    private String carModelName;

    /**
     * 车型图片
     */
    private String carModelImg;

    /**
     * 车座数
     */
    private Integer carSeatNum;

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
     * 省份ID
     */
    private Long provinceId;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 城市ID
     */
    private Long cityId;

    /**
     * 城市名称
     */
    private String cityName;


    /**
     * 车辆编码
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
     * 发动机号
     */
    private String carEngineNumber;

    /**
     * tbox设备品牌id
     */
    private Integer carEquipmentBrandId;

    /**
     * Tbox类型名称
     */
    private String tboxTypeName;

    /**
     * Tbox类型编码
     */
    private String tboxTypeCode;

    /**
     * Tbox的SIM卡号
     */
    private String tboxSimNumber;

    /**
     * 车机的SIM卡号
     */
    private String carTableSimNumber;

    /**
     * 所有权ID
     */
    private Long ownerId;

    /**
     * 所有权名称
     */
    private String ownerName;

    /**
     * 是否启用
     */
    private Integer open;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 网点ID
     */
    private Long stationId;

    /**
     * 起始运营时间
     */
    private Date useDate;

    /**
     * 关联客体
     */
    private String association;

    /**
     * 修改时间
     */
    private Date modifiedOn;

    /**
     * 修改人
     */
    private String modifiedBy;

    /**
     * 创建时间
     */
    private Date createdOn;

    /**
     * 创建人
     */
    private String createdBy;

}
