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
public class Parsecz implements Serializable {

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

    private String c;

    private String mn;

    private String p;

    private String si;

    private String siMn;

    private String s10mm;

    private String s70mm;

    /**
     * 混基重量
     */
    private String hjzl;

    /**
     * 干基重量
     */
    private String gjzl;

    /**
     * 矿点
     */
    private String kd;

    private String s50mm;

    /**
     * 硅
     */
    private String gui;

    /**
     * 碳
     */
    private String tan;

    /**
     * 磷
     */
    private String lin;

    /**
     * 锰
     */
    private String meng;

    /**
     * (粒度10)
     */
    private String ld10mm;

    /**
     * (粒度70)
     */
    private String ld70mm;

    /**
     * 全水分
     */
    private String quanshuifen;

    /**
     * 硅+锰
     */
    private String guimeng;

    /**
     * 硫
     */
    private String liu;

    private String matrlno;

    /**
     * (粒度50)
     */
    private String ld50mm;

    private String s60mm;

    private String si02;

    private String tfe;

    /**
     * 全铁粉
     */
    private String quantie;

    /**
     * 水分
     */
    private String shuifen;

    /**
     * 二氧化硅
     */
    private String guio2;

    /**
     * 全水分
     */
    private String qshuifen;

    private String k2o;

    private String k2on2o;

    private String na2o;

    private String tfesio2;

    private String tio2;

    private String zno;

    private String ni;


}
