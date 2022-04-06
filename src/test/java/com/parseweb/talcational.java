//package com.parseweb;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.parseweb.erp82.entity.EmployeesInfo02;
//import com.parseweb.erp82.mapper.EmployeesInfo02Mapper;
//import com.parseweb.lzih.entity.Xxclzdg;
//import com.parseweb.lzih.mapper.XxclzdgMapper;
//import com.parseweb.util.ERPHttpClient;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.util.ObjectUtils;
//
//@SpringBootTest
//public class talcational {
//    @Autowired
//    public XxclzdgMapper xxclzdgMapper;
//    @Autowired
//    public EmployeesInfo02Mapper employeesInfo02Mapper;
//
//    ERPHttpClient hc4erp;
//
//    final static String fwurl = "http://erp.ejianlong.com/hrn/hr/hrActionServlet/com.jl.hr.mobile.HrjcForMobile/getCompAllEmpInfo?compid=HP021129192848107";
//
//
//    @Test
//    public void insertxxclzdgfromexcels() {
//        employeesInfo02Mapper.delete(null);
//        hc4erp = new ERPHttpClient();
//        String s = hc4erp.doGet(fwurl);
//        JSONObject jo = JSONObject.parseObject(s);
//        JSONArray data = (JSONArray) jo.get("data");
//        for (int i = 0; i < data.size(); i++) {
//            JSONObject o = (JSONObject) data.get(i);
//            EmployeesInfo02 ei = new EmployeesInfo02();
//            ei.setArcAddr(o.getString("arc_addr"));
//            ei.setArcNo(o.getString("arc_no"));
//            ei.setArcTraDate(o.getString("arc_tra_date"));
//            ei.setBirCityId(o.getString("bir_city_id"));
//            ei.setBirCityIdName(o.getString("bir_city_id_name"));
//            ei.setBirProId(o.getString("bir_pro_id"));
//            ei.setBirProIdName(o.getString("bir_pro_id_name"));
//            ei.setBirth1Date(o.getString("birth1_date"));
//            ei.setBirthDate(o.getString("birth_date"));
//            ei.setBloodTypeId(o.getString("blood_type_id"));
//            ei.setBloodTypeIdName(o.getString("blood_type_id_name"));
//            ei.setBodyStaId(o.getString("body_sta_id"));
//            ei.setBodyStaIdName(o.getString("body_sta_id_name"));
//            ei.setCerNoi(o.getString("cerNo"));
//            ei.setCerno(o.getString("cer_no"));
//            ei.setCerTypeId(o.getString("cer_type_id"));
//            ei.setCerTypeId(o.getString("cer_type_id_name"));
//            ei.setCompid(o.getString("compid"));
//            ei.setCompuCer(o.getString("compu_cer"));
//            ei.setCompuDate(o.getString("compu_date"));
//            ei.setCompuGra(o.getString("compu_gra"));
//            ei.setComputType(o.getString("comput_type"));
//            ei.setCreateDate(o.getString("create_date"));
//            ei.setCreator(o.getString("creator"));
//            ei.setEmpname(o.getString("empName"));
//            ei.setEmpno(o.getString("empNo"));
//            ei.setEmpSouId(o.getString("emp_sou_id"));
//            ei.setEmpSouIdName(o.getString("emp_sou_id_name"));
//            ei.setEmpid(o.getString("empid"));
//            ei.setEngName(o.getString("eng_name"));
//            ei.setEntCorDatei(o.getString("entCorDate"));
//            ei.setEntcordate(o.getString("ent_cor_date"));
//            ei.setFea(o.getString("fea"));
//            ei.setFirDegreeId(o.getString("fir_degree_id"));
//            ei.setFirDegreeIdName(o.getString("fir_degree_id_name"));
//            ei.setFirDegreeSpecId("fir_degree_spec_id");
//            ei.setFirDegreeSpecIdName("fir_degree_spec_id_name");
//            ei.setFirDegreeSubSpecId(o.getString("fir_degree_sub_spec_id"));
//            ei.setFirDegreeSubSpecIdName(o.getString("fir_degree_sub_spec_id_name"));
//            ei.setFirExperEndDate(o.getString("fir_exper_end_date"));
//            ei.setFirExperId(o.getString("fir_exper_id"));
//            ei.setFirExperIdName(o.getString("fir_exper_id_name"));
//            ei.setFirExperSch(o.getString("fir_exper_sch"));
//            ei.setFirExperTrainStyleId(o.getString("fir_exper_train_style_id"));
//            ei.setFirExperTrainStyleIdName(o.getString("fir_exper_train_style_id_name"));
//            ei.setFirForLangCer(o.getString("fir_for_lang_cer"));
//            ei.setFirForLangDate(o.getString("fir_for_lang_date"));
//            ei.setFirForLangGraId(o.getString("fir_for_lang_gra_id"));
//            ei.setFirForLangGraIdName(o.getString("fir_for_lang_gra_id_name"));
//            ei.setFirForLangId(o.getString("fir_for_lang_id"));
//            ei.setFirForLangIdName(o.getString("fir_for_lang_id_name"));
//            ei.setGenderId(o.getString("gender_id"));
//            ei.setGenderIdName(o.getString("gender_id_name"));
//            ei.setHeight(o.getString("height"));
//            ei.setHomeAddr(o.getString("home_addr"));
//            ei.setHomePostCode(o.getString("home_post_code"));
//            ei.setHomeTele(o.getString("home_tele"));
//            ei.setIfRele(o.getString("if_rele"));
//            ei.setIfSick(o.getString("if_sick"));
//            ei.setIfSolar(o.getString("if_solar"));
//            ei.setImageurl(o.getString("imageURL"));
//            ei.setInsideLine(o.getString("inside_line"));
//            ei.setJoinWorkDate(o.getString("join_work_date"));
//            ei.setLastDegreeId(o.getString("last_degree_id"));
//            ei.setLastDegreeIdName(o.getString("last_degree_id_name"));
//            ei.setLastDegreeSpecId(o.getString("last_degree_spec_id"));
//            ei.setLastDegreeSpecIdName(o.getString("last_degree_spec_id_name"));
//            ei.setLastDegreeSubSpecId(o.getString("last_degree_sub_spec_id"));
//            ei.setLastDegreeSubSpecIdName(o.getString("last_degree_sub_spec_id_name"));
//            ei.setLastExperEndDate(o.getString("last_exper_end_date"));
//            ei.setLastExperId(o.getString("last_exper_id"));
//            ei.setLastExperIdName(o.getString("last_exper_id_name"));
//            ei.setLastExperSch(o.getString("last_exper_sch"));
//            ei.setLastExperTrainStyleId(o.getString("last_exper_train_style_id"));
//            ei.setLastExperTrainStyleIdName(o.getString("last_exper_train_style_id_name"));
//            ei.setMainorgcode(o.getString("mainOrgCode"));
//            ei.setMainorgid(o.getString("mainOrgId"));
//            ei.setMainpostid(o.getString("mainPostId"));
//            ei.setMainpostname(o.getString("mainPostName"));
//            String mainPostName = o.getString("mainPostName");
//            String[] split = mainPostName.split("-");
//            switch (split.length){
//                case 1:
//                    ei.setCompany(split[0]);
//                    break;
//                case 2:
//                    ei.setCompany(split[0]);
//                    ei.setPost05(split[1]);
//                    break;
//                case 3:
//                    ei.setCompany(split[0]);
//                    ei.setPost01(split[1]);
//                    ei.setPost05(split[2]);
//                    break;
//                case 4:
//                    ei.setCompany(split[0]);
//                    ei.setPost01(split[1]);
//                    ei.setPost02(split[2]);
//                    ei.setPost05(split[3]);
//                    break;
//                case 5:
//                    ei.setCompany(split[0]);
//                    ei.setPost01(split[1]);
//                    ei.setPost02(split[2]);
//                    ei.setPost03(split[3]);
//                    ei.setPost05(split[4]);
//                    break;
//                case 6:
//                    ei.setCompany(split[0]);
//                    ei.setPost01(split[1]);
//                    ei.setPost02(split[2]);
//                    ei.setPost03(split[3]);
//                    ei.setPost04(split[4]);
//                    ei.setPost05(split[5]);
//                    break;
//                default:
//            }
//            ei.setMarStaId(o.getString("mar_sta_id"));
//            ei.setMarStaIdName(o.getString("mar_sta_id_name"));
//            ei.setMobPhone(o.getString("mob_phone"));
//            ei.setNamePy(o.getString("name_py"));
//            ei.setNatId(o.getString("nat_id"));
//            ei.setNatPlaId(o.getString("nat_pla_id"));
//            ei.setNatPlaIdName(o.getString("nat_pla_id_name"));
//            ei.setNationId(o.getString("nation_id"));
//            ei.setNationIdName(o.getString("nation_id_name"));
//            ei.setNomComp(o.getString("nom_comp"));
//            ei.setNomName(o.getString("nom_name"));
//            ei.setNomRel(o.getString("nom_rel"));
//            ei.setNomTele(o.getString("nom_tele"));
//            ei.setNowAddr(o.getString("now_addr"));
//            ei.setNowAddrPostCode(o.getString("now_addr_post_code"));
//            ei.setNowAddrTele(o.getString("now_addr_tele"));
//            ei.setOldPosLevel(o.getString("oLD_POS_LEVEL"));
//            ei.setOldPosLevelId(o.getString("oLD_POS_LEVEL_ID"));
//            ei.setOffEmaili(o.getString("offEmail"));
//            ei.setOfftel(o.getString("offTel"));
//            ei.setOffemail(o.getString("off_email"));
//            ei.setOffPhone(o.getString("off_phone"));
//            ei.setOldName(o.getString("old_name"));
//            ei.setPerEamil(o.getString("per_eamil"));
//            ei.setPhoAddr(o.getString("pho_addr"));
//            ei.setPolStaId(o.getString("pol_sta_id"));
//            ei.setPolStaIdName(o.getString("pol_sta_id_name"));
//            ei.setRegCityId(o.getString("reg_city_id"));
//            ei.setRegCityIdName(o.getString("reg_city_id_name"));
//            ei.setRegFeaId(o.getString("reg_fea_id"));
//            ei.setRegFeaIdName(o.getString("reg_fea_id_name"));
//            ei.setRegPostNo(o.getString("reg_post_no"));
//            ei.setRegProId(o.getString("reg_pro_id"));
//            ei.setRegProIdName(o.getString("reg_pro_id_name"));
//            ei.setRelBelId(o.getString("rel_bel_id"));
//            ei.setRelBelIdName(o.getString("rel_bel_id_name"));
//            ei.setRelaComp(o.getString("rela_comp"));
//            ei.setRelaName(o.getString("rela_name"));
//            ei.setRelaRel(o.getString("rela_rel"));
//            ei.setRelaTele(o.getString("rela_tele"));
//            ei.setSecForLangCer(o.getString("sec_for_lang_cer"));
//            ei.setSecForLangDate(o.getString("sec_for_lang_date"));
//            ei.setSecForLangGraId(o.getString("sec_for_lang_gra_id"));
//            ei.setFirForLangGraIdName(o.getString("sec_for_lang_gra_id_name"));
//            ei.setSecForLangId(o.getString("sec_for_lang_id"));
//            ei.setSecForLangIdName(o.getString("sec_for_lang_id_name"));
//            ei.setSickDesc(o.getString("sick_desc"));
//            ei.setStatus(o.getString("status"));
//            ei.setT00hm001id(o.getString("t00hm001id"));
//            ei.setT00hp005name(o.getString("t00hp005Name"));
//            ei.setT00hp006name(o.getString("t00hp006Name"));
//            ei.setTel(o.getString("tel"));
//            ei.setUnreleRea(o.getString("unrele_rea"));
//            ei.setUrgCon(o.getString("urg_con"));
//            ei.setUrgConEmail(o.getString("urg_con_email"));
//            ei.setUrgConRel(o.getString("urg_con_rel"));
//            ei.setUrgConTele(o.getString("urg_con_tele"));
//            ei.setWeight(o.getString("weight"));
//            ei.setWorkStartDate(o.getString("work_start_date"));
//            employeesInfo02Mapper.insert(ei);
//        }
//    }
//}
