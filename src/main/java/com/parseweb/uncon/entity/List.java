package com.parseweb.uncon.entity;

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
 * @since 2021-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class List implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("webName")
    private String webname;

    @TableField("ipAddress")
    private String ipaddress;

    private String pageurl;

    @TableField("ipDatetime")
    private String ipdatetime;

    private String count;

    @TableField("pageDate")
    private String pagedate;

    @TableField("webUrl")
    private String weburl;

    private String date;


}
