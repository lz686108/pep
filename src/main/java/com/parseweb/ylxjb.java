package com.parseweb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.parseweb.lzih.entity.Jtgrs;
import com.parseweb.lzih.entity.Parsecz;
import com.parseweb.lzih.mapper.ParseczMapper;
import com.parseweb.util.ERPHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ylxjb {

    @Autowired
    public ParseczMapper parseczMapper;
    /*材料和编码*/
    /**
     * onclick="returnVal('5e01d7a3c5f74ef2889252a53da860ea','040807','FMG超特粉','','')"
     * onclick="returnVal('2ea11d29dea74a9c9f98ad74c5a31658','040818','FMG混合粉','','')"
     * onclick="returnVal('0044113cb65641998bad27af7246ea4b','040804','PB粉','','')"
     * onclick="returnVal('12d8a3eec7b54f018c09ac2c31d03b24','040823','阿提斯粉','','')"
     * onclick="returnVal('b22b71f87e6941989a46415593a2a3a5','040819','澳大利亚超特粉','','')"
     * onclick="returnVal('ddd5a6091c974243881a1939feb94100','040901','巴西粉','','')"
     * onclick="returnVal('b7b0f4e45efb4cd4862a27455a9d33d7','048701','金布巴粉','','')"
     * onclick="returnVal('feba2cb550664a3a826f28fc87e6ecea','048001','金步巴粉','','')"
     * onclick="returnVal('52ef4e1ea6bb4647a3a1cc32b284ab79','048501','罗伊山粉','','')"
     * onclick="returnVal('9fbfb08712fc4a118f59296d420ac19c','040817','麦克粉','','')"
     * onclick="returnVal('2f44400b94c744ac8fe3b13b41be76b2','045801','南非粉','','')"
     * onclick="returnVal('e48e0d501d4e46da907b2cd7eea4786f','048802','镍矿','','')"
     * onclick="returnVal('06188f6469284171ab71c56882bfd266','047001','纽曼粉','','')"
     * onclick="returnVal('eb6ce5a85efe4ec98ab9799aa0d31a9f','040111','硼铁精粉','','')"
     * onclick="returnVal('7541a91a4044404d9b2f37b6d1066f63','045702','塞拉利昂粉','','')"
     * onclick="returnVal('b35def7e52924a85a613be0df71b3f71','040116','铁精粉','TFe65%','')"
     * onclick="returnVal('5603d890412e4858b123b55517d13518','040109','铁精粉','TFe66%','')"
     */

    final String matrlID[] = {"5e01d7a3c5f74ef2889252a53da860ea", "2ea11d29dea74a9c9f98ad74c5a31658", "0044113cb65641998bad27af7246ea4b",
            "12d8a3eec7b54f018c09ac2c31d03b24", "b22b71f87e6941989a46415593a2a3a5", "ddd5a6091c974243881a1939feb94100", "b7b0f4e45efb4cd4862a27455a9d33d7",
            "feba2cb550664a3a826f28fc87e6ecea", "52ef4e1ea6bb4647a3a1cc32b284ab79", "9fbfb08712fc4a118f59296d420ac19c",
            "2f44400b94c744ac8fe3b13b41be76b2", "e48e0d501d4e46da907b2cd7eea4786f", "06188f6469284171ab71c56882bfd266",
            "eb6ce5a85efe4ec98ab9799aa0d31a9f", "7541a91a4044404d9b2f37b6d1066f63", "b35def7e52924a85a613be0df71b3f71",
            "5603d890412e4858b123b55517d13518"
    };

    final String matrlNo[] = {"040807", "040818", "040804", "040823", "040819",
            "040901", "048701", "048001", "048501", "040817", "045801", "048802", "047001", "040111", "045702", "040116", "040109"};

    static final String ERP_ENTRY = "http://erp.ejianlong.com/jlerp/mr/do?pageId=mrjj58ReportMain";

    String sql = " select sendTime, d.mtrlName, b.proName, b.shipperName, b.pact, b.chkNo, a.tradNo," +
            " g.mr02id, g.truckNo, a.mr04id, g.checkWNum, f.checkNum, f.inDate, f.mr06ID, f.mr05ID, " +
            "d.isUseFormula, b.pactId, a.creatDate,a.compId, \n" +
            "b.waterStand,a.mixNum, k.AUDMARK,b.mineName,f.cratDate from jlerp_db.tbmr04 a inner join " +
            "jlerp_db.tbmr01 b on a.mr01id=b.mr01id  inner join jlerp_db.tbmra11 d on b.mra1id=d.mra11id  inner join" +
            " jlerp_db.tbmr02 g on g.mr04id=a.mr04id  \n" +
            "left join jlerp_db.tbmr06 f on f.mr06ID=a.mr06id  left join jlerp_db.tbmr05 k on f.mr05ID=k.mr05id \n" +
            " where a.compid ='2df22a6f57fb4a6fbc83503189845932'  and g.sendTime >= 'qqqqqq'  " +
            "and g.sendTime <= 'dddddd'  and b.mra1ID = 'asdfghjkl'  order by sendTime," +
            " b.pact, b.chkNo, a.mr04ID, g.mr02id";

    String sqlHead = "select chkItem from jlerp_db.tbmr031 where compId = '2df22a6f57fb4a6fbc83503189845932' " +
            "and mra1ID = 'asdfghjkl' and status = 'Q' order by chkItem";

    String sqlMr03Info = "SELECT distinct a.mr04id, e.chkItem, c.checkQNum  FROM jlerp_db.tbmr04 a INNER JOIN " +
            "jlerp_db.tbmr01 b ON a.mr01id = b.mr01id INNER JOIN jlerp_db.tbmr03 c ON a.mr04id = c.mr04id  INNER JOIN " +
            "jlerp_db.tbmr031 e ON \n" +
            "e.mr031id = c.mr031id  INNER JOIN jlerp_db.tbmr02 g ON g.mr04id = a.mr04id  \n" +
            "where a.compid ='2df22a6f57fb4a6fbc83503189845932'  and g.sendTime >= 'qqqqqq'  and " +
            "g.sendTime <= 'dddddd'  and b.mra1ID = 'asdfghjkl'";

    ERPHttpClient hc4erp;

    String html;

    @Scheduled(cron = "0 0 23 * * ?")
    public void parsewebylxjb() {
        loginERP();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,-7);
        Date d = c.getTime();
        String day = format.format(d);
        String today = format.format(new Date());
        QueryWrapper<Parsecz> parseczqw = new QueryWrapper<>();
        parseczqw.between("rkrq",day,today);
        int delete = parseczMapper.delete(parseczqw);
        int nums = 0;
        for (int i = 0; i < matrlID.length; i++) {
            nums = i;
            for (int i1 = nums; i1 < matrlNo.length; i1++) {
                String sqlf = sql.replace("asdfghjkl", matrlID[i]);
                String sqlh = sqlHead.replace("asdfghjkl", matrlID[i]);
                String sqlm = sqlMr03Info.replace("asdfghjkl", matrlID[i]);
                String qqqqqq = sqlf.replace("qqqqqq", day);
                String dddddd = qqqqqq.replace("dddddd", today);
                String qqqqqq1 = sqlm.replace("qqqqqq", day);
                String dddddd1 = qqqqqq1.replace("dddddd", today);
                ArrayList<NameValuePair> nvp = new ArrayList<>();
                nvp.add(new BasicNameValuePair("flag", "R"));
                /*公司名称*/
                nvp.add(new BasicNameValuePair("compId", "2df22a6f57fb4a6fbc83503189845932"));
                nvp.add(new BasicNameValuePair("matrlID", matrlID[i]));
                nvp.add(new BasicNameValuePair("matrlNo", matrlNo[i1]));
                nvp.add(new BasicNameValuePair("dateFrom", day));
                nvp.add(new BasicNameValuePair("dateTo", today));
                nvp.add(new BasicNameValuePair("sql", dddddd));
                nvp.add(new BasicNameValuePair("sqlHead", sqlh));
                nvp.add(new BasicNameValuePair("sqlMr03Info", dddddd1));
                nvp.add(new BasicNameValuePair("type", "C"));
                html = hc4erp.doPost(ERP_ENTRY, nvp, "GBK");
                Document doc = Jsoup.parse(html);
                if ("040111".equals(matrlNo[i1]) || "045702".equals(matrlNo[i1]) || "040817".equals(matrlNo[i1]) ||
                        "040818".equals(matrlNo[i1]) || "040823".equals(matrlNo[i1]) || "040819".equals(matrlNo[i1]) ||
                        "047001".equals(matrlNo[i1]) || "040901".equals(matrlNo[i1]) || "048001".equals(matrlNo[i1]) ||
                        "048501".equals(matrlNo[i1])) {
                    Elements selecttr = doc.select("tr[class=light-bg]");
                    if (ObjectUtils.isEmpty(selecttr)) {
                        break;
                    }
                    for (int j = 0; j < selecttr.size(); j++) {
                        Parsecz pz = new Parsecz();
                        pz.setJjrq(selecttr.get(j).select("td").get(1).text());
                        pz.setRkrq(selecttr.get(j).select("td").get(2).text());
                        pz.setYlmc(selecttr.get(j).select("td").get(3).text());
                        pz.setGysmc(selecttr.get(j).select("td").get(4).text());
                        pz.setFhdd(selecttr.get(j).select("td").get(5).text());
                        pz.setJczt(selecttr.get(j).select("td").get(6).text());
                        pz.setHtbh(selecttr.get(j).select("td").get(7).text());
                        pz.setSldh(selecttr.get(j).select("td").get(8).text());
                        pz.setPh(selecttr.get(j).select("td").get(9).text());
                        pz.setCh(selecttr.get(j).select("td").get(10).text());
                        pz.setSi02(selecttr.get(j).select("td").get(11).text());
                        pz.setTfe(selecttr.get(j).select("td").get(12).text());
                        pz.setShuifen(selecttr.get(j).select("td").get(13).text());
                        pz.setHjzl(selecttr.get(j).select("td").get(14).text());
                        pz.setGjzl(selecttr.get(j).select("td").get(15).text());
                        pz.setKd(selecttr.get(j).select("td").get(16).text());
                        pz.setMatrlno(matrlNo[i1]);
                        parseczMapper.insert(pz);
                    }
                    break;
                }
                if ("040804".equals(matrlNo[i1])) {
                    Elements selecttr = doc.select("tr[class=light-bg]");
                    if (ObjectUtils.isEmpty(selecttr)) {
                        break;
                    }
                    for (int j = 0; j < selecttr.size(); j++) {
                        Parsecz pz = new Parsecz();
                        pz.setJjrq(selecttr.get(j).select("td").get(1).text());
                        pz.setRkrq(selecttr.get(j).select("td").get(2).text());
                        pz.setYlmc(selecttr.get(j).select("td").get(3).text());
                        pz.setGysmc(selecttr.get(j).select("td").get(4).text());
                        pz.setFhdd(selecttr.get(j).select("td").get(5).text());
                        pz.setJczt(selecttr.get(j).select("td").get(6).text());
                        pz.setHtbh(selecttr.get(j).select("td").get(7).text());
                        pz.setSldh(selecttr.get(j).select("td").get(8).text());
                        pz.setPh(selecttr.get(j).select("td").get(9).text());
                        pz.setCh(selecttr.get(j).select("td").get(10).text());
                        pz.setSi02(selecttr.get(j).select("td").get(11).text());
                        pz.setTfe(selecttr.get(j).select("td").get(12).text());
                        pz.setGuio2(selecttr.get(j).select("td").get(13).text());
                        pz.setQshuifen(selecttr.get(j).select("td").get(14).text());
                        pz.setQuantie(selecttr.get(j).select("td").get(15).text());
                        pz.setShuifen(selecttr.get(j).select("td").get(16).text());
                        pz.setHjzl(selecttr.get(j).select("td").get(17).text());
                        pz.setGjzl(selecttr.get(j).select("td").get(18).text());
                        pz.setKd(selecttr.get(j).select("td").get(19).text());
                        pz.setMatrlno(matrlNo[i1]);
                        parseczMapper.insert(pz);
                    }
                    break;
                }
                if ("048701".equals(matrlNo[i1])) {
                    Elements selecttr = doc.select("tr[class=light-bg]");
                    if (ObjectUtils.isEmpty(selecttr)) {
                        break;
                    }
                    for (int j = 0; j < selecttr.size(); j++) {
                        Parsecz pz = new Parsecz();
                        pz.setJjrq(selecttr.get(j).select("td").get(1).text());
                        pz.setRkrq(selecttr.get(j).select("td").get(2).text());
                        pz.setYlmc(selecttr.get(j).select("td").get(3).text());
                        pz.setGysmc(selecttr.get(j).select("td").get(4).text());
                        pz.setFhdd(selecttr.get(j).select("td").get(5).text());
                        pz.setJczt(selecttr.get(j).select("td").get(6).text());
                        pz.setHtbh(selecttr.get(j).select("td").get(7).text());
                        pz.setSldh(selecttr.get(j).select("td").get(8).text());
                        pz.setPh(selecttr.get(j).select("td").get(9).text());
                        pz.setCh(selecttr.get(j).select("td").get(10).text());
                        pz.setSi02(selecttr.get(j).select("td").get(11).text());
                        pz.setTfe(selecttr.get(j).select("td").get(12).text());
                        pz.setSi02(selecttr.get(j).select("td").get(13).text());
                        pz.setQuantie(selecttr.get(j).select("td").get(14).text());
                        pz.setShuifen(selecttr.get(j).select("td").get(15).text());
                        pz.setHjzl(selecttr.get(j).select("td").get(16).text());
                        pz.setGjzl(selecttr.get(j).select("td").get(17).text());
                        pz.setKd(selecttr.get(j).select("td").get(18).text());
                        pz.setMatrlno(matrlNo[i1]);
                        parseczMapper.insert(pz);
                    }
                    break;
                }
                if ("045801".equals(matrlNo[i1])) {
                    Elements selecttr = doc.select("tr[class=light-bg]");
                    if (ObjectUtils.isEmpty(selecttr)) {
                        break;
                    }
                    for (int j = 0; j < selecttr.size(); j++) {
                        Parsecz pz = new Parsecz();
                        pz.setJjrq(selecttr.get(j).select("td").get(1).text());
                        pz.setRkrq(selecttr.get(j).select("td").get(2).text());
                        pz.setYlmc(selecttr.get(j).select("td").get(3).text());
                        pz.setGysmc(selecttr.get(j).select("td").get(4).text());
                        pz.setFhdd(selecttr.get(j).select("td").get(5).text());
                        pz.setJczt(selecttr.get(j).select("td").get(6).text());
                        pz.setHtbh(selecttr.get(j).select("td").get(7).text());
                        pz.setSldh(selecttr.get(j).select("td").get(8).text());
                        pz.setPh(selecttr.get(j).select("td").get(9).text());
                        pz.setCh(selecttr.get(j).select("td").get(10).text());
                        pz.setSi(selecttr.get(j).select("td").get(11).text());
                        pz.setTfe(selecttr.get(j).select("td").get(12).text());
                        pz.setShuifen(selecttr.get(j).select("td").get(13).text());
                        pz.setHjzl(selecttr.get(j).select("td").get(14).text());
                        pz.setGjzl(selecttr.get(j).select("td").get(15).text());
                        pz.setKd(selecttr.get(j).select("td").get(16).text());
                        pz.setMatrlno(matrlNo[i1]);
                        parseczMapper.insert(pz);
                    }
                    break;
                }
                if ("048802".equals(matrlNo[i1])) {
                    Elements selecttr = doc.select("tr[class=light-bg]");
                    if (ObjectUtils.isEmpty(selecttr)) {
                        break;
                    }
                    for (int j = 0; j < selecttr.size(); j++) {
                        Parsecz pz = new Parsecz();
                        pz.setJjrq(selecttr.get(j).select("td").get(1).text());
                        pz.setRkrq(selecttr.get(j).select("td").get(2).text());
                        pz.setYlmc(selecttr.get(j).select("td").get(3).text());
                        pz.setGysmc(selecttr.get(j).select("td").get(4).text());
                        pz.setFhdd(selecttr.get(j).select("td").get(5).text());
                        pz.setJczt(selecttr.get(j).select("td").get(6).text());
                        pz.setHtbh(selecttr.get(j).select("td").get(7).text());
                        pz.setSldh(selecttr.get(j).select("td").get(8).text());
                        pz.setPh(selecttr.get(j).select("td").get(9).text());
                        pz.setCh(selecttr.get(j).select("td").get(10).text());
                        pz.setNi(selecttr.get(j).select("td").get(11).text());
                        pz.setTfe(selecttr.get(j).select("td").get(12).text());
                        pz.setShuifen(selecttr.get(j).select("td").get(13).text());
                        pz.setHjzl(selecttr.get(j).select("td").get(14).text());
                        pz.setGjzl(selecttr.get(j).select("td").get(15).text());
                        pz.setKd(selecttr.get(j).select("td").get(16).text());
                        pz.setMatrlno(matrlNo[i1]);
                        parseczMapper.insert(pz);
                    }
                    break;
                }
                if ("040116".equals(matrlNo[i1])||"040109".equals(matrlNo[i1])) {
                    Elements selecttr = doc.select("tr[class=light-bg]");
                    if (ObjectUtils.isEmpty(selecttr)) {
                        break;
                    }
                    for (int j = 0; j < selecttr.size(); j++) {
                        Parsecz pz = new Parsecz();
                        pz.setJjrq(selecttr.get(j).select("td").get(1).text());
                        pz.setRkrq(selecttr.get(j).select("td").get(2).text());
                        pz.setYlmc(selecttr.get(j).select("td").get(3).text());
                        pz.setGysmc(selecttr.get(j).select("td").get(4).text());
                        pz.setFhdd(selecttr.get(j).select("td").get(5).text());
                        pz.setJczt(selecttr.get(j).select("td").get(6).text());
                        pz.setHtbh(selecttr.get(j).select("td").get(7).text());
                        pz.setSldh(selecttr.get(j).select("td").get(8).text());
                        pz.setPh(selecttr.get(j).select("td").get(9).text());
                        pz.setCh(selecttr.get(j).select("td").get(10).text());
                        pz.setC(selecttr.get(j).select("td").get(11).text());
                        pz.setK2o(selecttr.get(j).select("td").get(12).text());
                        pz.setK2on2o(selecttr.get(j).select("td").get(13).text());
                        pz.setNa2o(selecttr.get(j).select("td").get(14).text());
                        pz.setLiu(selecttr.get(j).select("td").get(15).text());
                        pz.setSi02(selecttr.get(j).select("td").get(16).text());
                        pz.setTfe(selecttr.get(j).select("td").get(17).text());
                        pz.setTfesio2(selecttr.get(j).select("td").get(18).text());
                        pz.setTio2(selecttr.get(j).select("td").get(19).text());
                        pz.setZno(selecttr.get(j).select("td").get(20).text());
                        pz.setShuifen(selecttr.get(j).select("td").get(21).text());
                        pz.setHjzl(selecttr.get(j).select("td").get(22).text());
                        pz.setGjzl(selecttr.get(j).select("td").get(23).text());
                        pz.setKd(selecttr.get(j).select("td").get(24).text());
                        pz.setMatrlno(matrlNo[i1]);
                        parseczMapper.insert(pz);
                    }
                    break;
                }
            }
        }
    }

    public void loginERP() {
        hc4erp = new ERPHttpClient();
        // 其他位置不需要动,只修改账号密码
        hc4erp.loginERP("218708", "249346702");
        // 选择抚顺公司别
        hc4erp.doGet("http://erp.ejianlong.com/km/ka/html/kajjGateway.html?compId=HP021129192848107");
    }
}
