package com.parseweb.erp82.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class EmployeesInfo02 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String empid;

    @TableField("empNo")
    private String empno;

    @TableField("EmpName")
    private String empname;

    private String namePy;

    private String engName;

    @TableField("imageURL")
    private String imageurl;

    private String phoAddr;

    private String compid;

    @TableField("mainOrgCode")
    private String mainorgcode;

    @TableField("mainOrgId")
    private String mainorgid;

    @TableField("mainPostId")
    private String mainpostid;

    @TableField("mainPostName")
    private String mainpostname;

    private String company;

    private String post01;

    private String post02;

    private String post03;

    private String post04;

    private String post05;

    @TableField("oLD_POS_LEVEL_ID")
    private String oldPosLevelId;

    @TableField("oLD_POS_LEVEL")
    private String oldPosLevel;

    @TableField("t00hp005Name")
    private String t00hp005name;

    @TableField("t00hp006Name")
    private String t00hp006name;

    @TableField("offEmail")
    private String offemail;

    private String offEmaili;

    @TableField("offTel")
    private String offtel;

    private String offPhone;

    @TableField("entCorDate")
    private String entcordate;

    private String entCorDatei;

    private String createDate;

    private String creator;

    private String arcAddr;

    private String arcNo;

    private String arcTraDate;

    private String workStartDate;

    private String empSouId;

    private String empSouIdName;

    private String nomComp;

    private String nomName;

    private String nomRel;

    private String nomTele;

    private String ifRele;

    private String unreleRea;

    private String urgCon;

    private String urgConEmail;

    private String urgConRel;

    private String urgConTele;

    private String relaComp;

    private String relaName;

    private String relaRel;

    private String relaTele;

    private String ifSick;

    private String sickDesc;

    private String cerTypeId;

    @TableField("cerNo")
    private String cerno;

    private String cerNoi;

    private String genderId;

    private String genderIdName;

    private String height;

    private String weight;

    private String mobPhone;

    private String tel;

    private String oldName;

    private String perEamil;

    private String natId;

    private String natPlaId;

    private String natPlaIdName;

    private String nationId;

    private String nationIdName;

    private String polStaId;

    private String polStaIdName;

    private String homeAddr;

    private String homePostCode;

    private String homeTele;

    private String nowAddr;

    private String nowAddrPostCode;

    private String nowAddrTele;

    private String birCityId;

    private String birCityIdName;

    private String birProId;

    private String birProIdName;

    private String birth1Date;

    private String birthDate;

    private String ifSolar;

    private String regCityId;

    private String regCityIdName;

    private String regFeaId;

    private String regFeaIdName;

    private String regPostNo;

    private String regProId;

    private String regProIdName;

    private String bloodTypeId;

    private String bloodTypeIdName;

    private String bodyStaId;

    private String bodyStaIdName;

    private String marStaId;

    private String marStaIdName;

    private String relBelId;

    private String relBelIdName;

    private String joinWorkDate;

    private String firDegreeId;

    private String firDegreeIdName;

    private String firDegreeSpecId;

    private String firDegreeSpecIdName;

    private String firDegreeSubSpecId;

    private String firDegreeSubSpecIdName;

    private String firExperEndDate;

    private String firExperId;

    private String firExperIdName;

    private String firExperSch;

    private String firExperTrainStyleId;

    private String firExperTrainStyleIdName;

    private String firForLangCer;

    private String firForLangDate;

    private String firForLangGraId;

    private String firForLangGraIdName;

    private String firForLangId;

    private String firForLangIdName;

    private String secForLangCer;

    private String secForLangDate;

    private String secForLangGraId;

    private String secForLangGraIdName;

    private String secForLangId;

    private String secForLangIdName;

    private String lastDegreeId;

    private String lastDegreeIdName;

    private String lastDegreeSpecId;

    private String lastDegreeSpecIdName;

    private String lastDegreeSubSpecId;

    private String lastDegreeSubSpecIdName;

    private String lastExperEndDate;

    private String lastExperId;

    private String lastExperIdName;

    private String lastExperSch;

    private String lastExperTrainStyleId;

    private String lastExperTrainStyleIdName;

    private String compuCer;

    private String compuDate;

    private String compuGra;

    private String computType;

    private String fea;

    private String insideLine;

    private String status;

    private String t00hm001id;


}
