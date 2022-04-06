package com.parseweb.lzih.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizhen
 * @since 2022-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Yldhpc implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 检斤日期
     */
    private String prosecutorDate;

    /**
     * 入库日期
     */
    private String storageDate;

    /**
     * 原料名称
     */
    private String rawMaterialName;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 发货地点
     */
    private String departurePoint;

    /**
     * 进场状态
     */
    private String arenaStatus;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 收料单号
     */
    private String materialReceipt;

    /**
     * 批号
     */
    private String batchNum;

    /**
     * 车号
     */
    private String carNum;

    /**
     * >50mm
     */
    private String longMm;

    /**
     * Si
     */
    private String si;

    /**
     * 硅
     */
    private String gui;

    /**
     * <10mm
     */
    private String tmm;

    /**
     * 湿基重量
     */
    private String wetBasisWeight;

    /**
     * 干基重量
     */
    private String dryBasisWeight;

    /**
     * 矿点
     */
    private String mineralOccurrence;


}
