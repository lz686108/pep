package com.parseweb.uncon.entity;

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
 * @since 2021-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LowPower implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String date;

    private String time;

    private String number;

    private String kind;

    private String size;

    private String porosity;

    private String exeursion;

    private String bubble;

    private String crack;

    private String cCrack;

    private String metal;

    private String bCrack;

    private String centerCrack;

    private String username;

    private String unit;

    private String aa;

    private String flog;


}
