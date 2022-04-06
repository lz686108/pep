package com.parseweb.uncon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("TQualitative")
public class Tqualitative implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String val;

    private String code;

    private String cf;

    private String stand;

    private String time;

    private String show1;

    private String date;

    private String ban;

    private String process;


}
