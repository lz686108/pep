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
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String productType;

    private String clientTpe;

    private String client;

    private String orderNumber;

    private String steel;

    private String spec;

    private String sizing;

    private String weight;

    private String orderType;

    private String department;

    private String trayName;

    private String businessPersonnel;

    private String deliveryDate;

    private String deliveryArea;

    private String transportationMode;

    private String deliveryAddress;

    private String phone;

    private String deliveryUnit;

    private String salesPolicy;

    private String orderLevel;

    private String specialRequirements;

    private String contractNumber;

    private String remark;

    private String state;

    private String submissionTime;

    private String check;


}
