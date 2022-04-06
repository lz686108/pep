package com.parseweb.erp82.entity;

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
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialCodeCopy1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("matrlId")
    private String matrlid;

    @TableField("classID")
    private String classid;

    @TableField("classIndex")
    private String classindex;

    @TableField("classIndexDesc")
    private String classindexdesc;

    @TableField("kindID")
    private String kindid;

    @TableField("kindIndex")
    private String kindindex;

    @TableField("kindIndexDesc")
    private String kindindexdesc;

    @TableField("kindIDA")
    private String kindida;

    @TableField("kindAIndex")
    private String kindaindex;

    @TableField("kindAIndexDesc")
    private String kindaindexdesc;

    @TableField("itemID")
    private String itemid;

    @TableField("itemIndex")
    private String itemindex;

    @TableField("itemIndexDesc")
    private String itemindexdesc;

    @TableField("matrlNoID")
    private String matrlnoid;

    @TableId("matrlNo")
    private String matrlno;

    @TableField("chnName")
    private String chnname;

    @TableField("chnSpec")
    private String chnspec;

    private String unit;

    @TableField("preTime")
    private String pretime;

    private String status;

    @TableField("minStkQty")
    private String minstkqty;

    @TableField("abcClass")
    private String abcclass;


}
