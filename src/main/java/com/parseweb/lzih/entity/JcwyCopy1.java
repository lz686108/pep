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
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JcwyCopy1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 燃料类型
     */
    private String materialName;

    /**
     * 燃料名称
     */
    private String distributorName;

    /**
     * 归属地
     */
    private String gsdgils;

    /**
     * 付款方式
     */
    private String sterilise;

    /**
     * 合同价格
     */
    private String contractPrice;

    /**
     * 月计划量
     */
    private String monthAmount;

    /**
     * 结算水分
     */
    private String jssf;

    /**
     * 结算粉末
     */
    private String jsfm;

    /**
     * 版本号
     */
    private String version;

    /**
     * 基准值按钮
     */
    private String jzzaniu;


}
