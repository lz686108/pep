package com.parseweb.parseweb.entity;

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
 * @since 2021-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TruckLoading implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String productType;

    private String item;

    private String shift;

    private String carNo;

    private String loadingNumber;

    private String client;

    private String steel;

    private String spec;

    private String sizing;

    private String baleage;

    private String weight;

    private String storeroom;

    private String remark;

    private String date;


}
