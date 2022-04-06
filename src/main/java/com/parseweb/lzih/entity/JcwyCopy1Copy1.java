package com.parseweb.lzih.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class JcwyCopy1Copy1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 燃料类型
     */
    private String materialName;

    /**
     * 燃料名称
     */
    private String distributorName;

    /**
     * 付款方式
     */
    private String sterilise;

    /**
     * 合同价格
     */
    private String contractPrice;

    /**
     * 月计划量
     */
    private String monthAmount;

    /**
     * 结算水分
     */
    private String jssf;

    /**
     * 结算粉末
     */
    private String jsfm;

    /**
     * 版本号
     */
    private String version;

    /**
     * 计算合同价格
     */
    private String jshtjg;

    /**
     * 不含税价格
     */
    private String bhsjg;

    /**
     * 采购量
     */
    private String cgl;

    /**
     * 采购占比
     */
    private String cgzb;

    @TableField("Ad")
    private String ad;

    @TableField("Vdaf")
    private String vdaf;

    @TableField("Fcad")
    private String fcad;

    @TableField("Std")
    private String std;

    @TableField("M25")
    private String m25;

    @TableField("CRI")
    private String cri;

    @TableField("CSR")
    private String csr;

    @TableField("Mt")
    private String mt;

    @TableField("K")
    private String k;

    @TableField("Na")
    private String na;

    @TableField("Zn")
    private String zn;

    /**
     * 干焦比
     */
    private String gjb;

    /**
     * 净焦比
     */
    private String jjb;

    /**
     * 毛焦比
     */
    private String mjb;

    /**
     * 毛焦成本
     */
    private String mjcb;

    /**
     * 差异
     */
    private String cy;

    /**
     * 兑现率
     */
    private String dxl;

    /**
     * 归属地
     */
    private String gsdgils;


}
