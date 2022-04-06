package com.parseweb.erp82.entity;

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
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RawMaterialCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("mra1ID")
    private String mra1id;

    @TableField("classID")
    private String classid;

    @TableField("classIndex")
    private String classindex;

    @TableField("classIndexDesc")
    private String classindexdesc;

    @TableField("kindID")
    private String kindid;

    @TableField("kindIndex")
    private String kindindex;

    @TableField("kindIndexDesc")
    private String kindindexdesc;

    @TableField("mtrlNo")
    private String mtrlno;

    @TableField("mtrlName")
    private String mtrlname;

    private String spec;

    private String unit;

    private String status;


}
