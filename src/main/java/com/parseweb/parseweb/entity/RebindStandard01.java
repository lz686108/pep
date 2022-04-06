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
public class RebindStandard01 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 牌号
     */
    private String steel;

    /**
     * 规格
     */
    private String spec;

    /**
     * 定尺
     */
    private String sizing;

    /**
     * 捆重
     */
    private String rebind;


}