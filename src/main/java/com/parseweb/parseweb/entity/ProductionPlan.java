package com.parseweb.parseweb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
public class ProductionPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String type;

    private String productType;

    private String process;

    private String client;

    private String orderNumber;

    private String steel;

    private String spec;

    private String sizing;

    private String weight;

    private String dayPlan;

    private String orderType;

    private String storeroom;

    private String sort;

    private String state;

    private String remark;

    private LocalDate deliveryDate;

    private LocalDate date;

    private String updateId;


}
