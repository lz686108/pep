package com.parseweb.sjzh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.parseweb.lzih.entity.Gcilma;
import com.parseweb.lzih.entity.Jtgrs;
import com.parseweb.lzih.mapper.GcilmaMapper;
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

@Component
public class cicale {
    @Autowired
    public GcilmaMapper gcilmaMapper;

    ERPHttpClient hc4erp;

    String html;

    String matrlNo[] = {"020102", "020301", "021401"};

    String matrlID[] = {"14d60a9fd2bf4ea3aabeac1f22470cd4", "e0cbefff5921407f86bfdf3f23f3aa66", "e0cbefff5921407f86bfdf3f23f3aa66"};

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

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void gimle() {
        loginERP();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        String day = format.format(d);
        String today = format.format(new Date());
        QueryWrapper<Gcilma> jqw = new QueryWrapper<>();
        jqw.between("jjrq", day, today);
        gcilmaMapper.delete(jqw);
        for (int i = 0; i < matrlNo.length; i++) {
            for (int i1 = i; i1 < matrlID.length; i1++) {
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
                Elements selecttr = doc.select("tr[class=light-bg]");
                if (ObjectUtils.isEmpty(selecttr)) {
                    break;
                }
                if ("020102".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Gcilma jtgrs = new Gcilma();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkrq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(11).text());
                        jtgrs.setFcd(selecttr.get(j).select("td").get(12).text());
                        jtgrs.setMad(selecttr.get(j).select("td").get(13).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setQgrD(selecttr.get(j).select("td").get(15).text());
                        jtgrs.setQnetVAr(selecttr.get(j).select("td").get(16).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(19).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(20).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(21).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(22).text());
                        gcilmaMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("020301".equals(matrlNo[i1]) || "021401".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Gcilma jtgrs = new Gcilma();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkrq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(11).text());
                        jtgrs.setFcd(selecttr.get(j).select("td").get(12).text());
                        jtgrs.setMad(selecttr.get(j).select("td").get(13).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setQgrD(selecttr.get(j).select("td").get(15).text());
                        jtgrs.setQnetVAr(selecttr.get(j).select("td").get(16).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(17).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(19).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(20).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(21).text());
                        gcilmaMapper.insert(jtgrs);
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
