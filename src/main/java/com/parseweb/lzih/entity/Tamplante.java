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
public class Tamplante implements Serializable {

    private static final long serialVersionUID = 1L;

    private String items;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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


}
