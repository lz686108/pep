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
public class Unconerp implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * fs号
     */
    private String number;

    /**
     * 元素名称
     */
    private String item;

    /**
     * 价格
     */
    private String price;

    /**
     * 供应商名称
     */
    private String gysmc;

    /**
     * 样品名称
     */
    private String ypmc;

    /**
     * 日期
     */
    private String date;


}
