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
public class Xxclzdg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("cerNo")
    private String cerno;

    @TableField("cerTypeId")
    private String certypeid;

    private String compid;

    @TableField("empName")
    private String empname;

    @TableField("empNo")
    private String empno;

    private String empid;

    @TableField("entCorDate")
    private String entcordate;

    @TableField("firExper")
    private String firexper;

    private String gender;

    @TableField("imageURL")
    private String imageurl;

    @TableField("lastExper")
    private String lastexper;

    @TableField("mainOrgCode")
    private String mainorgcode;

    @TableField("mainOrgId")
    private String mainorgid;

    @TableField("mainOrgName")
    private String mainorgname;

    @TableField("mainPostId")
    private String mainpostid;

    @TableField("mainPostName")
    private String mainpostname;

    @TableField("namePy")
    private String namepy;

    @TableField("natPla")
    private String natpla;

    @TableField("offEmail")
    private String offemail;

    @TableField("offTel")
    private String offtel;

    @TableField("orgFullName")
    private String orgfullname;

    private String tel;

    /**
     * 存表日期
     */
    private String cbrq;


}
