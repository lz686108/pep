package com.parseweb.sjzh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.parseweb.lzih.entity.*;
import com.parseweb.lzih.mapper.*;
import com.parseweb.uncon.mapper.UnconventionalityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class upylxjbcat {
    @Autowired
    public LurufMapper lurufMapper;
    @Autowired
    public ButvaluMapper butvaluMapper;
    @Autowired
    public LurufcopMapper lurufcopMapper;
    @Autowired
    public ParseczMapper parseczMapper;
    @Autowired
    public UnconventionalityMapper unconventionalityMapper;
    @Autowired
    public BizhbMapper bizhbMapper;
    @Autowired
    public UnconerpMapper unconerpMapper;
    @Autowired
    public BzbiaoMapper bzbiaoMapper;
    @Autowired
    public UpcattalbeMapper upcattalbeMapper;
    @Autowired
    public LurusMapper lurusMapper;
    @Autowired
    public Ucp1Mapper ucp1Mapper;

    final String gysmclista[] = {"北票合兴实业有限公司", "鞍钢集团矿业弓长岭有限公司", "鞍钢集团矿业有限公司", "罕王实业集团（抚顺）矿业有限公司", "本溪盛源矿业有限责任公司",
            "鞍山金和矿业有限公司", "本溪同成铁选有限公司", "灯塔市众鑫选矿厂", "辽阳顺锋钢铁有限公司", "铁岭县红印铁矿有限公司", "本溪鑫盛矿业有限责任公司",
            "本溪同成铁选有限公司", "清原满族自治县鑫福经贸有限公司", "本溪永亿铁选有限公司", "桓仁矿业有限公司", "沈阳东洋炼钢公用设施有限公司", "沈阳浑南矿业有限责任公司",
            "本溪东方三家子矿业有限公司", "抚顺莱河矿业有限公司", "本溪智华矿产品有限公司", "抚顺县鑫隆铁矿石粉碎厂", "抚顺诚泰矿业贸易有限公司",
            "本溪市惠兴矿业有限责任公司", "本溪鹏达矿业有限公司", "本溪思山岭云新矿业有限公司", "本溪龙新矿业有限公司", "本溪鹏达矿业有限公司",
            "辽阳市文圣区宏光炉料加工厂", "灯塔市银盛建衡矿业有限公司", "西钢集团灯塔矿业有限公司", "后英集团鞍山活龙矿业有限公司", "本溪市永安铁选厂",
            "辽阳县天利矿业有限公司"
    };
    final String gysmclistb[] = {"FMG超特粉", "塞拉利昂粉", "PB粉", "FMG混合粉", "印度粉", "罗伊山粉", "阿提斯粉", "金布巴粉", "麦克粉",
            "纽曼粉", "巴西卡粉","WPF粉"};

    final String iglist[] = {
            "-2.78", "-2.4", "-2.4", "-2.7", "-2.34", "-2.34", "-2.66", "-2.8", "-2.3", "-2.73", "-2.8", "-2.66", "-2.2", "-2.54",
            "-2.4", "-2.2", "-2.54", "-2.4", "-2.2", "-2.66", "-2.73", "-2.2", "-2.63", "-2.4", "-2.4", "-2.2", "-2.8", "-2.8",
            "-2.45", "-2.88", "-2.84", "-2.45", "-2.54"
    };

    final String gyiglist[] = {"9", "7.43", "4.5", "8.5", "4.5", "6", "5.05", "5.05", "6", "4.58", "4.58"};

    @Scheduled(cron = "0 0 12 * * ?")
    public void inserttastelad() {
        //       全查询版本号
        QueryWrapper<Lurufcop> lws = new QueryWrapper<>();
        lws.groupBy("versions");
        List<Lurufcop> lurufcops = lurufcopMapper.selectList(lws);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < lurufcops.size(); i++) {
            strings.add(lurufcops.get(i).getVersions());
        }
        ucp1Mapper.delete(null);
        for (int i = 0; i < strings.size(); i++) {
            String banbenh = strings.get(i);
            ArrayList<Map<String, Object>> maps = selectTableBybbh(banbenh);
            for (int i1 = 0; i1 < maps.size(); i1++) {
                Map<String, Object> som = maps.get(i1);
                Ucp1 ue = new Ucp1();
                boolean al2O3 = ObjectUtils.isEmpty(som.get("Al2O3"));
                ue.setAl2o3(al2O3 ? "0" : som.get("Al2O3").toString());
                boolean CaO = ObjectUtils.isEmpty(som.get("CaO"));
                ue.setCao(CaO ? "0" : som.get("CaO").toString());
                boolean Ig = ObjectUtils.isEmpty(som.get("Ig"));
                ue.setIg(CaO ? "0" : som.get("Ig").toString());
                boolean K2O = ObjectUtils.isEmpty(som.get("K2O"));
                ue.setK2o(K2O ? "0" : som.get("K2O").toString());
                boolean MgO = ObjectUtils.isEmpty(som.get("MgO"));
                ue.setMgo(MgO ? "0" : som.get("MgO").toString());
                boolean MnO = ObjectUtils.isEmpty(som.get("MnO"));
                ue.setMno(MnO ? "0" : som.get("MnO").toString());
                boolean Na2O = ObjectUtils.isEmpty(som.get("Na2O"));
                ue.setNa2o(Na2O ? "0" : som.get("Na2O").toString());
                boolean P = ObjectUtils.isEmpty(som.get("P"));
                ue.setP(P ? "0" : som.get("P").toString());
                boolean ZnO = ObjectUtils.isEmpty(som.get("ZnO"));
                ue.setZno(ZnO ? "0" : som.get("ZnO").toString());
                boolean gysmc = ObjectUtils.isEmpty(som.get("gysmc"));
                ue.setGysmc(gysmc ? "0" : som.get("gysmc").toString());
                boolean htjg = ObjectUtils.isEmpty(som.get("htjg"));
                ue.setHtjg(htjg ? "0" : som.get("htjg").toString());
                boolean jhdxl = ObjectUtils.isEmpty(som.get("jhdxl"));
                ue.setJhdxl(jhdxl ? "0" : som.get("jhdxl").toString());
                boolean liu = ObjectUtils.isEmpty(som.get("liu"));
                ue.setLiu(liu ? "0" : som.get("liu").toString());
                boolean ljqd = ObjectUtils.isEmpty(som.get("ljqd"));
                ue.setLjqd(ljqd ? "0" : som.get("ljqd").toString());
                boolean shuifen = ObjectUtils.isEmpty(som.get("shuifen"));
                ue.setShuifen(shuifen ? "0" : som.get("shuifen").toString());
                boolean sio2 = ObjectUtils.isEmpty(som.get("sio2"));
                ue.setSio2(sio2 ? "0" : som.get("sio2").toString());
                boolean sjdxl = ObjectUtils.isEmpty(som.get("sjdxl"));
                ue.setSjdxl(sjdxl ? "0" : som.get("sjdxl").toString());
                boolean tfe = ObjectUtils.isEmpty(som.get("tfe"));
                ue.setTfe(tfe ? "0" : som.get("tfe").toString());
                boolean thxwd = ObjectUtils.isEmpty(som.get("thxwd"));
                ue.setThxwd(thxwd ? "0" : som.get("thxwd").toString());
                boolean tio2 = ObjectUtils.isEmpty(som.get("tio2"));
                ue.setTio2(tio2 ? "0" : som.get("tio2").toString());
                boolean yxldx = ObjectUtils.isEmpty(som.get("yxldx"));
                ue.setYxldx(yxldx ? "0" : som.get("yxldx").toString());
                boolean yxpw = ObjectUtils.isEmpty(som.get("yxpw"));
                ue.setYxpw(yxpw ? "0" : som.get("yxpw").toString());
                boolean yxpwduj = ObjectUtils.isEmpty(som.get("yxpwduj"));
                ue.setYxpwduj(yxpwduj ? "0" : som.get("yxpwduj").toString());
                boolean zjxqd = ObjectUtils.isEmpty(som.get("zjxqd"));
                ue.setZjxqd(zjxqd ? "0" : som.get("zjxqd").toString());
                ue.setBbh(banbenh);
                ue.setSterilise(som.get("sterilise").toString());
                ucp1Mapper.insert(ue);
            }
        }
    }

    public ArrayList<Map<String, Object>> selectTableBybbh(String banbenh) {
        QueryWrapper<Lurus> lwq = new QueryWrapper<>();
        lwq.eq("versions", banbenh);
        List<Lurus> lurusList = lurusMapper.selectList(lwq);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        for (int i = 0; i < lurusList.size(); i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sterilise", lurusList.get(i).getSterilise());

            /*供应商名称*/
            List<String> strings1 = Arrays.asList(gysmclistb);
            boolean contains1 = strings1.contains(lurusList.get(i).getMaterialName());
            /*判断是否是外粉,如果是外粉供应商名称写物料名称*/
            if (contains1) {
                hashMap.put("gysmc", lurusList.get(i).getMaterialName());
            } else {
                hashMap.put("gysmc", lurusList.get(i).getDistributorName());
            }
            /*去lurufcop表查询对应的合同价格*/
            QueryWrapper<Lurufcop> lqw = new QueryWrapper<>();
            lqw.eq("versions", lurusList.get(i).getVersions());
            lqw.eq("material_name", lurusList.get(i).getMaterialName());
            lqw.eq("distributor_name", lurusList.get(i).getDistributorName());
            List<Lurufcop> lurufcops = lurufcopMapper.selectList(lqw);
            Lurufcop lurufcop = new Lurufcop();
            if (!ObjectUtils.isEmpty(lurufcops)) {
                lurufcop = lurufcops.get(0);
            }
            hashMap.put("htjg", lurufcop.getContractPrice());
            /*计划兑现率
            当期天数/月天数（如5月10日测算，计划兑现率为10/31）*/
            String dates = banbenh.substring(8, 10);
            String mouths = banbenh.substring(5, 7);
            String years = banbenh.substring(0, 4);
            if (dates.indexOf(0) == 0) {
                dates = banbenh.substring(9, 10);
            }
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(years));
            cal.set(Calendar.MONTH, Integer.parseInt(mouths));
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            /*set(int field, int value) - 是用来设置"年/月/日/小时/分钟/秒/微秒"等值*/
            /*add(int field, int amount)  add 可以对 Calendar 的字段进行计算。如果需要减去值，那么使用负数值就可以了*/
            Date lastDate = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(lastDate);
            String semounth = format.substring(8, 10);
            if (semounth.indexOf(0) == 0) {
                semounth = format.substring(9, 10);
            }
            DecimalFormat df = new DecimalFormat("0.00");
            Double aDouble = Double.valueOf(dates);
            Double aDouble1 = Double.valueOf(semounth);
            hashMap.put("jhdxl", df.format((aDouble / aDouble1) * 100) + "" + '%');
            /*重新获取物料和样品名称*/
            String materialName = lurusList.get(i).getMaterialName();
            String distributorName = lurusList.get(i).getDistributorName();
            /*判断数组种是否包含物料名称*/
            List<String> strings2 = Arrays.asList(gysmclistb);
            boolean contains2 = strings2.contains(materialName);
            /*新建的非常规表里取元素品位*/
            /*线给hashmap存入非常规的excel元素名称,如果新的非常规表中有值则会替换hashmap中的值*/
            QueryWrapper<Bizhb> bizhbQueryWrapper = new QueryWrapper<>();
            String tvb = distributorName;
            if (contains2) {
                tvb = materialName;
            }
            bizhbQueryWrapper.eq("gysmc", tvb);
            List<Bizhb> bizhbs1 = bizhbMapper.selectList(bizhbQueryWrapper);
            if (!ObjectUtils.isEmpty(bizhbs1)) {
                Bizhb bizhb = bizhbs1.get(0);
                /*常规*/
                hashMap.put("tfe", bizhb.getTfe());
                hashMap.put("sio2", bizhb.getSi02());
                hashMap.put("tio2", bizhb.getTio2());
                hashMap.put("shuifen", bizhb.getShuifen());
                hashMap.put("liu", bizhb.getLiu());
                /*非常规*/
                hashMap.put("CaO", bizhb.getCao());
                hashMap.put("MgO", bizhb.getMgo());
                hashMap.put("Al2O3", bizhb.getAl2o3());
                hashMap.put("P", bizhb.getP());
                hashMap.put("K2O", bizhb.getK2o());
                hashMap.put("Na2O", bizhb.getNa2o());
                hashMap.put("ZnO", bizhb.getZno());
                hashMap.put("MnO", bizhb.getMno());
                hashMap.put("Ig", bizhb.getIg());
            }
            /*常规数据的替换*/
            /*国粉根据公司和样品名称去常规表表取数据*/
            /*外粉则根据原料名称单独取常规表取数据,最新七天求平均数*/
            QueryWrapper<Parsecz> parseczqw = new QueryWrapper<>();
            parseczqw.eq("ylmc", materialName);
            if (!contains2) {
                parseczqw.eq("gysmc", distributorName);
            }
            parseczqw.last("limit 7");
            parseczqw.orderByDesc("jjrq");
            List<Parsecz> parseczs = parseczMapper.selectList(parseczqw);
            if (ObjectUtils.isEmpty(parseczs) && ObjectUtils.isEmpty(bizhbs1)) {
                /*同化性温度*/
                hashMap.put("thxwd", lurusList.get(i).getChemotropism());
                /*连晶强度*/
                hashMap.put("ljqd", lurusList.get(i).getCrystalStock());
                /*液相流动性*/
                hashMap.put("yxldx", lurusList.get(i).getLiquidFluidity());
                /*粘结相强度*/
                hashMap.put("zjxqd", lurusList.get(i).getPhaseStrength());
                maps.add(hashMap);
                continue;
            }
            /*取出来的集合取常规元素如果为空给默认值*/
            ArrayList<String> tfe = new ArrayList<>();
            ArrayList<String> sio2 = new ArrayList<>();
            ArrayList<String> tio2 = new ArrayList<>();
            ArrayList<String> s = new ArrayList<>();
            ArrayList<String> h2o = new ArrayList<>();
            for (int i1 = 0; i1 < parseczs.size(); i1++) {
                if (!ObjectUtils.isEmpty(parseczs.get(i1).getTfe())) {
                    tfe.add(parseczs.get(i1).getTfe());
                }
                if (!ObjectUtils.isEmpty(parseczs.get(i1).getSi02())) {
                    sio2.add(parseczs.get(i1).getSi02());
                }
                if (!ObjectUtils.isEmpty(parseczs.get(i1).getTio2())) {
                    tio2.add(parseczs.get(i1).getTio2());
                }
                if (!ObjectUtils.isEmpty(parseczs.get(i1).getLiu())) {
                    s.add(parseczs.get(i1).getLiu());
                }
                if (!ObjectUtils.isEmpty(parseczs.get(i1).getShuifen())) {
                    h2o.add(parseczs.get(i1).getShuifen());
                }
            }
            Double tfed = 0.0;
            Double sio2d = 0.0;
            Double tio2d = 0.0;
            Double sd = 0.0;
            Double h2od = 0.0;
            Double hjzl = 0.0;
            /*如果有为空的情况 去数据库查询元素的标注值*/
            List<Bizhb> bizhbs = bizhbMapper.selectList(null);
            for (int i1 = 0; i1 < tfe.size(); i1++) {
                tfed = tfed + Double.valueOf(tfe.get(i1));
            }
            if (ObjectUtils.isEmpty(tfe)) {
                for (int i1 = 0; i1 < bizhbs.size(); i1++) {
                    if (distributorName.equals(bizhbs.get(i1).getGysmc())) {
                        hashMap.put("tfe", bizhbs.get(i1).getTfe());
                        break;
                    }
                }
            } else {
                hashMap.put("tfe", df.format(tfed / tfe.size()));
            }
            for (int i1 = 0; i1 < sio2.size(); i1++) {
                sio2d = sio2d + Double.valueOf(sio2.get(i1));
            }
            if (ObjectUtils.isEmpty(sio2)) {
                for (int i1 = 0; i1 < bizhbs.size(); i1++) {
                    if (distributorName.equals(bizhbs.get(i1).getGysmc())) {
                        hashMap.put("sio2", bizhbs.get(i1).getSi02());
                        break;
                    }
                }
            } else {
                hashMap.put("sio2", df.format(sio2d / sio2.size()));
            }
            for (int i1 = 0; i1 < tio2.size(); i1++) {
                tio2d = tio2d + Double.valueOf(tio2.get(i1));
            }
            if (ObjectUtils.isEmpty(tio2)) {
                for (int i1 = 0; i1 < bizhbs.size(); i1++) {
                    if (distributorName.equals(bizhbs.get(i1).getGysmc())) {
                        hashMap.put("tio2", bizhbs.get(i1).getTio2());
                        break;
                    }
                }
            } else {
                hashMap.put("tio2", df.format(tio2d / tio2.size()));
            }
            for (int i1 = 0; i1 < s.size(); i1++) {
                sd = sd + Double.valueOf(s.get(i1));
            }
            if (ObjectUtils.isEmpty(s)) {
                for (int i1 = 0; i1 < bizhbs.size(); i1++) {
                    if (distributorName.equals(bizhbs.get(i1).getGysmc())) {
                        hashMap.put("liu", bizhbs.get(i1).getLiu());
                        break;
                    }
                }
            } else {
                hashMap.put("liu", df.format(sd / s.size()));
            }
            for (int i1 = 0; i1 < h2o.size(); i1++) {
                h2od = h2od + Double.valueOf(h2o.get(i1));
            }
            if (ObjectUtils.isEmpty(h2o)) {
                for (int i1 = 0; i1 < bizhbs.size(); i1++) {
                    if (distributorName.equals(bizhbs.get(i1).getGysmc())) {
                        hashMap.put("shuifen", bizhbs.get(i1).getShuifen());
                        break;
                    }
                }
            } else {
                hashMap.put("shuifen", df.format(h2od / h2o.size()));
            }
            /*混基重量当月从当月第一天开始垒加,最后除月计划量*/
            Calendar instance = Calendar.getInstance();
            instance.set(Calendar.YEAR, Integer.parseInt(banbenh.substring(0, 4)));
            instance.set(Calendar.MONTH, Integer.parseInt(banbenh.substring(5, 7)));
            instance.set(Calendar.DAY_OF_MONTH, 1);
            instance.add(Calendar.MONTH, -1);
            String format1 = sdf.format(instance.getTime());
            QueryWrapper<Parsecz> parseczqw1 = new QueryWrapper<>();
            parseczqw1.eq("ylmc", materialName);
            if (!contains2) {
                parseczqw1.eq("gysmc", distributorName);
            }
            parseczqw1.between("rkrq", format1, banbenh);
            List<Parsecz> parseczs1 = parseczMapper.selectList(parseczqw1);
            Double hjzls = 0.0;
            for (int i1 = 0; i1 < parseczs1.size(); i1++) {
                String hjzl1 = parseczs1.get(i1).getHjzl();
                if (ObjectUtils.isEmpty(hjzl1)) {
                    hjzls = hjzls + 0.0;
                } else {
                    hjzls = hjzls + Double.valueOf(parseczs1.get(i1).getHjzl());
                }
            }
            /*实际兑现率 实际到货量（爬取）/月计划量*/
            String monthAmount = lurufcop.getMonthAmount();
            if (!ObjectUtils.isEmpty(monthAmount)) {
                hashMap.put("sjdxl", df.format((hjzls / Double.valueOf(monthAmount)) * 100) + "" + "%");
            }
            /*新表的非常规数据转换*/
            QueryWrapper<Unconerp> uwq = new QueryWrapper<>();
            uwq.eq("ypmc", materialName);
            if (!contains2) {
                uwq.eq("gysmc", distributorName);
            }
            List<Unconerp> unconerps = unconerpMapper.selectList(uwq);
            if (ObjectUtils.isEmpty(unconerps) && ObjectUtils.isEmpty(bizhbs1)) {
                /*同化性温度*/
                hashMap.put("thxwd", lurusList.get(i).getChemotropism());
                /*连晶强度*/
                hashMap.put("ljqd", lurusList.get(i).getCrystalStock());
                /*液相流动性*/
                hashMap.put("yxldx", lurusList.get(i).getLiquidFluidity());
                /*粘结相强度*/
                hashMap.put("zjxqd", lurusList.get(i).getPhaseStrength());
                maps.add(hashMap);
                continue;
            }
            if (!ObjectUtils.isEmpty(unconerps)) {
                for (int i1 = 0; i1 < unconerps.size(); i1++) {
                    hashMap.put(unconerps.get(i1).getItem(), unconerps.get(i1).getPrice());
                }
            }
            /*同化性温度*/
            hashMap.put("thxwd", lurusList.get(i).getChemotropism());
            /*连晶强度*/
            hashMap.put("ljqd", lurusList.get(i).getCrystalStock());
            /*液相流动性*/
            hashMap.put("yxldx", lurusList.get(i).getLiquidFluidity());
            /*粘结相强度*/
            hashMap.put("zjxqd", lurusList.get(i).getPhaseStrength());
            /*有效品味*/
            Double tfe1 = Double.valueOf((String) hashMap.get("tfe"));
            Double sio21 = Double.valueOf((String) hashMap.get("sio2"));
            Double al2O31 = Double.valueOf((String) hashMap.get("Al2O3"));
            Double ig1 = Double.valueOf((String) hashMap.get("Ig"));
            Double caO1 = Double.valueOf((String) hashMap.get("CaO"));
            Double mgO1 = Double.valueOf((String) hashMap.get("MgO"));
            Double mn1 = Double.valueOf((String) hashMap.get("MnO"));
            Double p1 = Double.valueOf((String) hashMap.get("P"));
            Double tio21 = Double.valueOf((String) hashMap.get("tio2"));
            Double k2O1 = Double.valueOf((String) hashMap.get("K2O"));
            Double na2O1 = Double.valueOf((String) hashMap.get("Na2O"));
            Double znO1 = Double.valueOf((String) hashMap.get("ZnO"));
            Double s1 = Double.valueOf((String) hashMap.get("liu"));
            Double yxpw = tfe1 / (1 - ig1 / 100) / ((100 + 2 * 1.035 * (sio21 + al2O31) / (1 - ig1 / 100)) - 2 * (caO1 + mgO1 + mn1) /
                    (1 - ig1 / 100) + (3 * s1 + 2 * p1) / (1 - ig1 / 100) + 2 * tio21 / (1 - ig1 / 100) + 3 * (k2O1 + na2O1 + znO1) / (1 - ig1 / 100)) * 100;
            hashMap.put("yxpw", df.format(yxpw));
            /*品味加价后价格*/
            Double tfe2 = Double.valueOf(String.valueOf(hashMap.get("tfe")));
            Double purqua = Double.valueOf(0);
            if (!ObjectUtils.isEmpty(lurusList.get(i).getPurchasingQuality())) {
                purqua = Double.valueOf(lurusList.get(i).getPurchasingQuality());
            }
            Double s2 = Double.valueOf(String.valueOf(hashMap.get("liu")));
            Double tio22 = Double.valueOf(String.valueOf(hashMap.get("tio2")));
            if (s2 > 1) {
                s2 = 25 + (s2 - 1) * 100;
            }
            if (0.5 < s2 && s2 < 1) {
                s2 = (s2 - 0.5) * 50;
            }
            if (s2 < 0.5) {
                s2 = 0.0;
            }
            if (tio22 < 0.4) {
                tio22 = 0.0;
            } else {
                tio22 = (tio22 - 0.4) * 200;
            }
            Double jjhjg = (tfe2 - purqua) * 10 + Double.valueOf(lurufcop.getContractPrice()) - s2 - tio22;
            if (contains2 || "一单户".equals(lurusList.get(i).getPricingType())) {
                /*有效吨度价*/
                hashMap.put("yxpwduj", df.format(Double.valueOf(lurufcop.getContractPrice()) / yxpw));
            } else {
                hashMap.put("yxpwduj", df.format(jjhjg / yxpw));
            }
            maps.add(hashMap);
        }
        Collections.sort(maps, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = 0.00;
                Double name2 = 0.00;
                if (ObjectUtils.isEmpty(o1.get("yxpwduj")) || ObjectUtils.isEmpty(o2.get("yxpwduj"))) {
                    name1 = 0.00;
                    name2 = 0.00;
                } else {
                    name1 = Double.parseDouble((String) o1.get("yxpwduj"));
                    name2 = Double.parseDouble((String) o2.get("yxpwduj"));
                }
                return name1.compareTo(name2);

            }
        });
        return maps;
    }

}
