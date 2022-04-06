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
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Gcilma implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 检斤日期
     */
    private String jjrq;

    /**
     * 入库日期
     */
    private String rkrq;

    /**
     * 原料名称
     */
    private String ylmc;

    /**
     * 供应商名称
     */
    private String gysmc;

    /**
     * 发货地点
     */
    private String fhdd;

    /**
     * 进场状态
     */
    private String jczt;

    /**
     * 合同编号
     */
    private String htbh;

    /**
     * 收料单号
     */
    private String sldh;

    /**
     * 批号
     */
    private String ph;

    /**
     * 车号
     */
    private String ch;

    private String ad;

    private String fcd;

    private String mad;

    private String mt;

    private String qgrD;

    private String qnetVAr;

    private String stD;

    private String vdaf;

    /**
     * 湿基重量
     */
    private String sjzl;

    /**
     * 干基重量
     */
    private String gjzl;

    /**
     * 矿点
     */
    private String kd;


}
