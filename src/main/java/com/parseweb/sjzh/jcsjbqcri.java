package com.parseweb.sjzh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.parseweb.lzih.entity.Jtgrs;
import com.parseweb.lzih.entity.Parsecz;
import com.parseweb.lzih.mapper.JtgrsMapper;
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
public class jcsjbqcri {
    @Autowired
    public JtgrsMapper jtgrsMapper;

    final String matrlID[] = {"14d60a9fd2bf4ea3aabeac1f22470cd4", "e0cbefff5921407f86bfdf3f23f3aa66",
            "1858fd620c514685b969642da606749c", "621e89e768024fa8bab8225ed1705210",
            "a55ab87ea1bf49bc955210fc901edd22", "515c47ff9972451aa3ca0e215bb6952b",
            "6390ed118a26492a87adb1ebcea251e0", "fdf4be0e907140c6a1315ff4338dabd3",
            "cf4192e286504d898752ae1da8d98f82", "1a80d16cb8ae47298d96acdb44442851","ecdf043322a14215a80c3e685104412c"};

    final String matrlNo[] = {"20102", "20301", "21401", "30110", "30114", "30124", "30131", "30132", "30133", "30141","30130"};

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

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void titjccrs() {

        loginERP();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        String day = format.format(d);
        String today = format.format(new Date());
        QueryWrapper<Jtgrs> jqw = new QueryWrapper<>();
        jqw.between("jjrq", day, today);
        jtgrsMapper.delete(jqw);
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
                Elements selecttr = doc.select("tr[class=light-bg]");
                if (ObjectUtils.isEmpty(selecttr)) {
                    break;
                }
                if ("30141".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(11).text());
                        jtgrs.setCri(selecttr.get(j).select("td").get(12).text());
                        jtgrs.setCsr(selecttr.get(j).select("td").get(13).text());
                        jtgrs.setM25(selecttr.get(j).select("td").get(16).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(19).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(20).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(21).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(26).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(27).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(28).text());
                        jtgrsMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("30132".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(13).text());
                        jtgrs.setCri(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setCsr(selecttr.get(j).select("td").get(15).text());
                        jtgrs.setM25(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(21).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(22).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(23).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(39).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(40).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(41).text());
                        jtgrsMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("30131".equals(matrlNo[i1]) || "30133".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(13).text());
                        jtgrs.setCri(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setCsr(selecttr.get(j).select("td").get(15).text());
                        jtgrs.setM25(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(21).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(22).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(23).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(40).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(41).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(42).text());
                        jtgrsMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("30124".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(13).text());
                        jtgrs.setCri(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setCsr(selecttr.get(j).select("td").get(15).text());
                        jtgrs.setM25(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(21).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(22).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(23).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(31).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(32).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(33).text());
                        jtgrsMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("20301".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(11).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(17).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(19).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(20).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(21).text());
                        jtgrsMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("20102".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(11).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(19).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(20).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(21).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(22).text());
                        jtgrsMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("21401".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(11).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(19).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(21).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(22).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(23).text());
                        jtgrsMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("30110".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(13).text());
                        jtgrs.setCri(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setCsr(selecttr.get(j).select("td").get(15).text());
                        jtgrs.setM25(selecttr.get(j).select("td").get(16).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(17).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(19).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(39).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(40).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(41).text());
                        jtgrsMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("30114".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(13).text());
                        jtgrs.setCri(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setCsr(selecttr.get(j).select("td").get(15).text());
                        jtgrs.setM25(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(21).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(22).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(23).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(41).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(42).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(43).text());
                        jtgrsMapper.insert(jtgrs);
                    }
                    break;
                }
                if ("30130".equals(matrlNo[i1])) {
                    for (int j = 0; j < selecttr.size(); j++) {
                        Jtgrs jtgrs = new Jtgrs();
                        jtgrs.setJjrq(selecttr.get(j).select("td").get(1).text());
                        jtgrs.setRkriq(selecttr.get(j).select("td").get(2).text());
                        jtgrs.setYlmc(selecttr.get(j).select("td").get(3).text());
                        jtgrs.setGysmc(selecttr.get(j).select("td").get(4).text());
                        jtgrs.setFhdd(selecttr.get(j).select("td").get(5).text());
                        jtgrs.setJczt(selecttr.get(j).select("td").get(6).text());
                        jtgrs.setHtbh(selecttr.get(j).select("td").get(7).text());
                        jtgrs.setSldh(selecttr.get(j).select("td").get(8).text());
                        jtgrs.setPh(selecttr.get(j).select("td").get(9).text());
                        jtgrs.setCh(selecttr.get(j).select("td").get(10).text());
                        jtgrs.setAd(selecttr.get(j).select("td").get(13).text());
                        jtgrs.setCri(selecttr.get(j).select("td").get(14).text());
                        jtgrs.setCsr(selecttr.get(j).select("td").get(15).text());
                        jtgrs.setM25(selecttr.get(j).select("td").get(18).text());
                        jtgrs.setMt(selecttr.get(j).select("td").get(21).text());
                        jtgrs.setStD(selecttr.get(j).select("td").get(22).text());
                        jtgrs.setVdaf(selecttr.get(j).select("td").get(23).text());
                        jtgrs.setSjzl(selecttr.get(j).select("td").get(42).text());
                        jtgrs.setGjzl(selecttr.get(j).select("td").get(43).text());
                        jtgrs.setKd(selecttr.get(j).select("td").get(44).text());
                        jtgrsMapper.insert(jtgrs);
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
