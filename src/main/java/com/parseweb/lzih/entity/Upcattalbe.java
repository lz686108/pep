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
 * @since 2022-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Upcattalbe implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String gysmc;

    private String htjg;

    private String yxpw;

    private String yxpwduj;

    private String jhdxl;

    private String sjdxl;

    private String tfe;

    @TableField("CaO")
    private String cao;

    private String sio2;

    @TableField("MgO")
    private String mgo;

    @TableField("Al2O3")
    private String al2o3;

    private String tio2;

    @TableField("P")
    private String p;

    @TableField("K2O")
    private String k2o;

    @TableField("Na2O")
    private String na2o;

    @TableField("ZnO")
    private String zno;

    @TableField("MnO")
    private String mno;

    private String shuifen;

    @TableField("Ig")
    private String ig;

    private String liu;

    /**
     * 同性化温度
     */
    private String thxwd;

    /**
     * 连晶强度
     */
    private String ljqd;

    /**
     * 液相流动性
     */
    private String yxldx;

    /**
     * 粘结相强度
     */
    private String zjxqd;

    /**
     * 版本号
     */
    private String bbh;

    /**
     * 种类
     */
    private String sterilise;


}
