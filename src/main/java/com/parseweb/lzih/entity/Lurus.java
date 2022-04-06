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
public class Lurus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 供应商名称
     */
    private String distributorName;

    /**
     * 料种
     */
    private String sterilise;

    /**
     * 同化性温度
     */
    private String chemotropism;

    /**
     * 连晶温度
     */
    private String crystalStock;

    /**
     * 液相流动性
     */
    private String liquidFluidity;

    /**
     * 粘结相强度
     */
    private String phaseStrength;

    /**
     * 定价方式
     */
    private String pricingType;

    /**
     * 采购质量(1%)
     */
    private String purchasingQuality;

    /**
     * 版本号
     */
    private String versions;


}
