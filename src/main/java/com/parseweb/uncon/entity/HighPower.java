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
public class HighPower implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String date;

    private String time;

    private String usrname;

    private String process;

    private String number;

    private String mark;

    private String size;

    private String center;

    private String side;

    private String centre;

    private String side0l;

    private String productionNumber;

    private String cc;


}
