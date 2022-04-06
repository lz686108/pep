package com.parseweb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.parseweb.parseweb.entity.WorkSheet;
import com.parseweb.parseweb.entity.WorkSheet02;
import com.parseweb.parseweb.mapper.WorkSheetMapper;
import com.parseweb.util.ERPHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ds {
    private static final Logger logger = LoggerFactory.getLogger("Service");

    // 抚顺新钢铁有限责任公司 抚顺新钢铁线材制造有限公司
    static String[] company = {"HP021129192848107", "fushunxiancaiid"};
    // static String[] company = { "HP021129192848107"};

    // G_螺纹钢 GM_锚杆钢 高线 钢坯 盘螺
    /*static String[] comID = {"00978a07d1384b1ec314de4295dab3bc", "b8d8997d46084f43bd4b6d8f162da42d",
            "5882623a616143658b85ee2894b39ff9", "cf58f469f051b06d209762a2f2c0c59e",
            "181a93326d6544d5a45677b12de1aeb5"};*/
    static final String ERP_HOST = "http://erp.ejianlong.com";

    static final String FILE_ROOT = "testdata//";

    static final String ERP_ENTRY = "http://erp.ejianlong.com/jlerp/st/do?pageId=stjj0901";

    static final String DaYin = "http://erp.ejianlong.com/jlerp/st/do?pageId=stjj0902Print&flag=P&compId=HP021129192848107&st09Id=cb3ecf6d8d224fca8e770a5479346f9f";

    ERPHttpClient hc4erp;

    String html;

    @Autowired
    public WorkSheetMapper workSheetMapper;

   @Scheduled(fixedRate = 1000 * 60 * 60)
    /*@Scheduled定时执行该方法*/
    public void contextLoads() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        QueryWrapper<WorkSheet> workSheetQueryWrapper = new QueryWrapper<>();
        workSheetQueryWrapper.like("loading_time", format);
        workSheetMapper.delete(workSheetQueryWrapper);
        loginERP();
//        String yesStr1 = TimeKit.parseDate(TimeKit.nextDate(LocalDate.now(), -1),
//                TimeKit.TimeFormat.SHORT_DATE_PATTERN_LINE);
        String localFile = "";
        /*装车完成,已销账*/
        String a[] = {"1", "2"};
        /*GM_锚杆钢,I_高线(改盘圆),I高线,L盘螺*/
        String[] comID = {"00978a07d1384b1ec314de4295dab3bc", "b8d8997d46084f43bd4b6d8f162da42d",
                "5882623a616143658b85ee2894b39ff9", "181a93326d6544d5a45677b12de1aeb5"};
        for (int i3 = 0; i3 < comID.length; i3++) {
            for (int i2 = 0; i2 < a.length; i2++) {
                List<NameValuePair> nvp = new ArrayList<NameValuePair>();
                nvp.add(new BasicNameValuePair("flag", "R"));
                nvp.add(new BasicNameValuePair("compId_qry", "HP021129192848107"));
                nvp.add(new BasicNameValuePair("productGenusId_qry", comID[i3]));
                nvp.add(new BasicNameValuePair("state_qry", a[i2]));
                nvp.add(new BasicNameValuePair("loadDateSt_qry", format));
                nvp.add(new BasicNameValuePair("loadDateEd_qry", format));
                nvp.add(new BasicNameValuePair("isHasException_qry", "false"));
                html = hc4erp.doPost(ERP_ENTRY, nvp, "GBK");
                /*static final String ERP_ENTRY = "http://erp.ejianlong.com/jlerp/st/do?pageId=stjj0901";*/
                Document doc = Jsoup.parse(html);
                //<input type="hidden" name="filePath" value="/file/sg/doc">
                Elements tr = doc.select("table[id=xpTable]").first().select("tr");
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 1; i < tr.size(); i++) {
                    strings.add(doc.select("table[id=xpTable]").first().select("tr").get(i).attr("onclick"));
                }
                ArrayList<String> newstring = new ArrayList<>();
                for (int i = 0; i < strings.size(); i++) {
                    newstring.add(strings.get(i).split("'")[3]);
                }
                ArrayList<String> filePathList = new ArrayList<>();
                for (int i = 0; i < newstring.size(); i++) {
                    String urlssSsss = "http://erp.ejianlong.com/jlerp/st/do?pageId=stjj0902Print&flag=P&compId=HP021129192848107&st09Id=" + newstring.get(i);
                    String s = hc4erp.doGet(urlssSsss);
                    Document parse = Jsoup.parse(s);
                    /*从第五行开始 前四行没用*/
                    Elements table = parse.select("table").get(5).select("tr");
                    Elements table1 = parse.select("table").get(6).select("tr");
                    Elements table2 = parse.select("table").get(7).select("tr");
                    WorkSheet workSheet = new WorkSheet();
                    String attr = table.get(0).select("td").get(1).text();
                    /*客户名称*/
                    workSheet.setClient(attr);
                    /*厂别*/
                    String td = table.get(0).select("td").get(3).text();
                    workSheet.setProcess(td);
                    /*装车作业单号*/
                    String td1 = table.get(1).select("td").get(1).text();
                    workSheet.setWorkSheetNum(td1);
                    /*合同项次号*/
                    String td2 = table.get(1).select("td").get(3).text();
                    workSheet.setContractItemNum(td2);
                    /*产品分类*/
                    String td3 = table.get(1).select("td").get(5).text();
                    workSheet.setProductType(td3);
                    /*发货通知单号*/
                    String td4 = table.get(2).select("td").get(1).text();
                    workSheet.setShippingNoticeNum(td4);
                    /*标准全名*/
                    String td5 = table.get(2).select("td").get(3).text();
                    workSheet.setStandard(td5);
                    /*提货经办人*/
                    String td6 = table.get(2).select("td").get(5).text();
                    workSheet.setAgent(td6);
                    /*运输方式*/
                    String td7 = table.get(3).select("td").get(1).text();
                    workSheet.setTransportationMode(td7);
                    /*车号*/
                    String td8 = table.get(3).select("td").get(3).text();
                    workSheet.setCarNum(td8);
                    /*联系人/电话*/
                    String td9 = table.get(3).select("td").get(5).text();
                    workSheet.setLinkmanTelepone(td9);
                    /*地点代码*/
                    String td10 = table.get(4).select("td").get(1).text();
                    workSheet.setPlaceNum(td10);
                    /*专用线*/
                    String td11 = table.get(4).select("td").get(3).text();
                    workSheet.setPrivateWire(td11);
                    /*实际重量(吨)*/
                    String td12 = table.get(4).select("td").get(5).text();
                    workSheet.setActualWeight(td12);
                    /*收货地址 数据库中没有该字段*/
                    /*炉批号表中没有*/
                    /*牌号*/
                    String td13 = table1.get(1).select("td").get(2).text();
                    workSheet.setSteel(td13);
                    /*规格*/
                    String td14 = table1.get(1).select("td").get(3).text();
                    workSheet.setSpec(td14);
                    /*品质*/
                    String td15 = table1.get(1).select("td").get(4).text();
                    workSheet.setQuality(td15);
                    /*理论重量*/
                    String td16 = table1.get(table1.size() - 2).select("td").get(1).text();
                    workSheet.setTheoryWeight(td16);
                    /*件数*/
                    String td17 = table1.get(table1.size() - 2).select("td").get(2).text();
                    workSheet.setBaleage(td17);
                    /*备注*/
                    String td18 = table1.get(table1.size() - 1).select("td").get(1).text();
                    workSheet.setRemark(td18);
                    /*装车人*/
                    String td19 = table2.get(0).select("td").get(0).text();
                    workSheet.setLoadingPeople(td19);
                    /*装车时间*/
                    String td20 = table2.get(0).select("td").get(1).text();
                    workSheet.setLoadingTime(td20);
                    int insert = workSheetMapper.insert(workSheet);
                }
            }
        }
        updatename();
        filtr();
    }

    /**
     * @param
     * @return 登入erp
     */
    public void loginERP() {
        hc4erp = new ERPHttpClient();
        // 其他位置不需要动,只修改账号密码
        hc4erp.loginERP("217677", "dwg211505");
        // 选择抚顺公司别
        hc4erp.doGet("http://erp.ejianlong.com/km/ka/html/kajjGateway.html?compId=HP021129192848107");
    }

    /**
     * @param
     * @return 如果同一天存在兩條相同的装车作业单号, 则过滤掉第一条, 保留第二条并且存入新的数据库
     */
    public void filtr() {
        List<WorkSheet> workSheets = workSheetMapper.selectList(null);
        if (!ObjectUtils.isEmpty(workSheets)) {
            for (int i = 0; i < workSheets.size(); i++) {
                String workSheetNum = workSheets.get(i).getWorkSheetNum();
                String loadingTime = workSheets.get(i).getLoadingTime();
                if (ObjectUtils.isEmpty(loadingTime) || ObjectUtils.isEmpty(workSheetNum)){
                    continue;
                }
                String substring = loadingTime.substring(5, 15);
                QueryWrapper<WorkSheet> wr = new QueryWrapper<>();
                wr.eq("work_sheet_num", workSheetNum);
                wr.like("loading_time", loadingTime);
                List<WorkSheet> workSheets1 = workSheetMapper.selectList(wr);
                if (workSheets1.size() <= 1) {
                    continue;
                }
                Integer id = workSheets.get(i).getId();
                QueryWrapper<WorkSheet> wid = new QueryWrapper<>();
                wid.eq("id", id);
                workSheetMapper.delete(wid);
                QueryWrapper<WorkSheet> workSheetQueryWrapper = new QueryWrapper<>();
                workSheetQueryWrapper.eq("work_sheet_num", workSheetNum);
                workSheetQueryWrapper.like("loading_time", loadingTime);
                List<WorkSheet> workSheets2 = workSheetMapper.selectList(workSheetQueryWrapper);
                if (!ObjectUtils.isEmpty(workSheets2)) {
                    WorkSheet02 workSheet02 = new WorkSheet02();
                    workSheet02.setClient(workSheets2.get(0).getClient());
                    workSheet02.setProcess(workSheets2.get(0).getProcess());
                    workSheet02.setWorkSheetNum(workSheets2.get(0).getWorkSheetNum());
                    workSheet02.setContractItemNum(workSheets2.get(0).getContractItemNum());
                    workSheet02.setProductType(workSheets2.get(0).getProductType());
                    workSheet02.setShippingNoticeNum(workSheets2.get(0).getShippingNoticeNum());
                    workSheet02.setStandard(workSheets2.get(0).getStandard());
                    workSheet02.setAgent(workSheets2.get(0).getAgent());
                    workSheet02.setTransportationMode(workSheets2.get(0).getTransportationMode());
                    workSheet02.setCarNum(workSheets2.get(0).getCarNum());
                    workSheet02.setLinkmanTelepone(workSheets2.get(0).getLinkmanTelepone());
                    workSheet02.setPlaceNum(workSheets2.get(0).getWorkSheetNum());
                    workSheet02.setPrivateWire(workSheets2.get(0).getPrivateWire());
                    workSheet02.setActualWeight(workSheets2.get(0).getActualWeight());
                    workSheet02.setSteel(workSheets2.get(0).getSteel());
                    workSheet02.setSpec(workSheets2.get(0).getSpec());
                    workSheet02.setQuality(workSheets2.get(0).getQuality());
                    workSheet02.setTheoryWeight(workSheets2.get(0).getTheoryWeight());
                    workSheet02.setBaleage(workSheets2.get(0).getBaleage());
                    workSheet02.setRemark(workSheets2.get(0).getRemark());
                    workSheet02.setLoadingPeople(workSheets2.get(0).getLoadingPeople());
                    workSheet02.setLoadingTime(workSheets2.get(0).getLoadingTime());
                    /*现实际理论重量*/
                    workSheet02.setActualWeight02(workSheets2.get(0).getActualWeight());
                    workSheet02.setTheoryWeight02(workSheets2.get(0).getTheoryWeight());
                    workSheet02.setBaleage02(workSheets2.get(0).getBaleage());
                }
            }
        }
    }

    /**
     * @param
     * @return 高线改盘圆
     */
    public void updatename() {
        List<WorkSheet> workSheets = workSheetMapper.selectList(null);
        ArrayList<Integer> idlist = new ArrayList<>();
        for (int i = 0; i < workSheets.size(); i++) {
            if ("高线".equals(workSheets.get(i).getProductType())) {
                idlist.add(workSheets.get(i).getId());
            }
        }
        for (int i = 0; i < idlist.size(); i++) {
            QueryWrapper<WorkSheet> wrapper = new QueryWrapper<>();
            wrapper.eq("id", idlist.get(i));
            WorkSheet workSheet = new WorkSheet();
            workSheet.setProductType("盘圆");
            workSheetMapper.update(workSheet, wrapper);
        }
    }

    /**
     * @return 移库装车作业单
     * @author
     */
    public void contextLoads1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        QueryWrapper<WorkSheet> workSheetQueryWrapper = new QueryWrapper<>();
        workSheetQueryWrapper.like("loading_time", format);
        workSheetMapper.delete(workSheetQueryWrapper);
        loginERP();
//        String yesStr1 = TimeKit.parseDate(TimeKit.nextDate(LocalDate.now(), -1),
//                TimeKit.TimeFormat.SHORT_DATE_PATTERN_LINE);
        String localFile = "";
        /*装车完成,已销账*/
        String a[] = {"1", "2"};
        /*GM_锚杆钢,I_高线(改盘圆),I高线,L盘螺*/
        String[] comID = {"00978a07d1384b1ec314de4295dab3bc", "b8d8997d46084f43bd4b6d8f162da42d",
                "5882623a616143658b85ee2894b39ff9", "181a93326d6544d5a45677b12de1aeb5"};
        for (int i3 = 0; i3 < comID.length; i3++) {
            for (int i2 = 0; i2 < a.length; i2++) {
                List<NameValuePair> nvp = new ArrayList<NameValuePair>();
                nvp.add(new BasicNameValuePair("flag", "R"));
                nvp.add(new BasicNameValuePair("compId_qry", "HP021129192848107"));
                nvp.add(new BasicNameValuePair("productGenusId_qry", comID[i3]));
                nvp.add(new BasicNameValuePair("state_qry", a[i2]));
                nvp.add(new BasicNameValuePair("loadDateSt_qry", format));
                nvp.add(new BasicNameValuePair("loadDateEd_qry", format));
                nvp.add(new BasicNameValuePair("isHasException_qry", "false"));
                html = hc4erp.doPost(ERP_ENTRY, nvp, "GBK");
                Document doc = Jsoup.parse(html);
                //<input type="hidden" name="filePath" value="/file/sg/doc">
                Elements tr = doc.select("table[id=xpTable]").first().select("tr");
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 1; i < tr.size(); i++) {
                    strings.add(doc.select("table[id=xpTable]").first().select("tr").get(i).attr("onclick"));
                }
                ArrayList<String> newstring = new ArrayList<>();
                for (int i = 0; i < strings.size(); i++) {
                    newstring.add(strings.get(i).split("'")[3]);
                }
                ArrayList<String> filePathList = new ArrayList<>();
                for (int i = 0; i < newstring.size(); i++) {
                    String urlssSsss = "http://erp.ejianlong.com/jlerp/st/do?pageId=stjj0902Print&flag=P&compId=HP021129192848107&st09Id=" + newstring.get(i);
                    String s = hc4erp.doGet(urlssSsss);
                    Document parse = Jsoup.parse(s);
                    /*从第五行开始 前四行没用*/
                    Elements table = parse.select("table").get(5).select("tr");
                    /*
                     * 第二个表格
                     * */
                    Elements table1 = parse.select("table").get(6).select("tr");
                    Elements table2 = parse.select("table").get(7).select("tr");
                    WorkSheet workSheet = new WorkSheet();
                    String attr = table.get(0).select("td").get(1).text();
                    /*客户名称*/
                    workSheet.setClient(attr);
//                    /*厂别*/
//                    String td = table.get(0).select("td").get(3).text();
//                    workSheet.setProcess(td);
                    /*装车作业单号*/
                    String td1 = table.get(1).select("td").get(1).text();
                    workSheet.setWorkSheetNum(td1);
                    /*移库计划单号*/
                    String td2 = table.get(1).select("td").get(3).text();
                    workSheet.setContractItemNum(td2);
                    /*产品分类*/
                    String td3 = table.get(1).select("td").get(5).text();
                    workSheet.setProductType(td3);
                    /*发货通知单号*/
                    String td4 = table.get(2).select("td").get(1).text();
                    workSheet.setShippingNoticeNum(td4);
                    /*标准全名*/
                    String td5 = table.get(2).select("td").get(3).text();
                    workSheet.setStandard(td5);
                    /*提货经办人*/
                    String td6 = table.get(2).select("td").get(5).text();
                    workSheet.setAgent(td6);
                    /*运输方式*/
                    String td7 = table.get(3).select("td").get(1).text();
                    workSheet.setTransportationMode(td7);
                    /*车号*/
                    String td8 = table.get(3).select("td").get(3).text();
                    workSheet.setCarNum(td8);
                    /*联系人/电话*/
                    String td9 = table.get(3).select("td").get(5).text();
                    workSheet.setLinkmanTelepone(td9);
                    /*地点代码*/
                    String td10 = table.get(4).select("td").get(1).text();
                    workSheet.setPlaceNum(td10);
                    /*专用线*/
                    String td11 = table.get(4).select("td").get(3).text();
                    workSheet.setPrivateWire(td11);
                    /*实际重量(吨)*/
                    String td12 = table.get(4).select("td").get(5).text();
                    workSheet.setActualWeight(td12);
                    /*收货地址 数据库中没有该字段*/
                    /*炉批号表中没有*/
                    /*牌号*/
                    String td13 = table1.get(1).select("td").get(2).text();
                    workSheet.setSteel(td13);
                    /*规格*/
                    String td14 = table1.get(1).select("td").get(3).text();
                    workSheet.setSpec(td14);
                    /*品质*/
                    String td15 = table1.get(1).select("td").get(4).text();
                    workSheet.setQuality(td15);
                    /*理论重量*/
                    String td16 = table1.get(table1.size() - 2).select("td").get(1).text();
                    workSheet.setTheoryWeight(td16);
                    /*件数*/
                    String td17 = table1.get(table1.size() - 2).select("td").get(2).text();
                    workSheet.setBaleage(td17);
                    /*备注*/
                    String td18 = table1.get(table1.size() - 1).select("td").get(1).text();
                    workSheet.setRemark(td18);
                    /*装车人*/
                    String td19 = table2.get(0).select("td").get(0).text();
                    workSheet.setLoadingPeople(td19);
                    /*装车时间*/
                    String td20 = table2.get(0).select("td").get(1).text();
                    workSheet.setLoadingTime(td20);
                    int insert = workSheetMapper.insert(workSheet);
                }
            }
        }
        updatename();
        filtr();
    }
}
