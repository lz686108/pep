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
public class WorkSheet02 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String client;

    private String process;

    private String workSheetNum;

    private String contractItemNum;

    private String productType;

    private String shippingNoticeNum;

    private String standard;

    private String agent;

    private String transportationMode;

    private String carNum;

    private String linkmanTelepone;

    private String placeNum;

    private String privateWire;

    private String actualWeight;

    private String steel;

    private String spec;

    private String quality;

    private String theoryWeight;

    private String baleage;

    private String remark;

    private String loadingPeople;

    private String loadingTime;

    private String actualWeight02;

    private String theoryWeight02;

    private String baleage02;

    private LocalDate date;


}
