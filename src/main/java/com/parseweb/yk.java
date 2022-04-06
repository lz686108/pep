package com.parseweb;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.parseweb.parseweb.entity.Transfer;
import com.parseweb.parseweb.entity.WorkSheet;
import com.parseweb.parseweb.entity.WorkSheet02;
import com.parseweb.parseweb.mapper.TransferMapper;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class yk {

    private static final Logger logger = LoggerFactory.getLogger("Service");

    // 抚顺新钢铁有限责任公司 抚顺新钢铁线材制造有限公司
    static String[] company = {"HP021129192848107", "fushunxiancaiid"};
    // static String[] company = { "HP021129192848107"};

    // G_螺纹钢 GM_锚杆钢 高线 钢坯 盘螺
    static String[] comID = {"00978a07d1384b1ec314de4295dab3bc", "b8d8997d46084f43bd4b6d8f162da42d",
            "5882623a616143658b85ee2894b39ff9", "cf58f469f051b06d209762a2f2c0c59e",
            "181a93326d6544d5a45677b12de1aeb5"};


    static final String ERP_HOST = "http://erp.ejianlong.com";

    static final String FILE_ROOT = "testdata//";

    static final String ERP_ENTRY = "http://erp.ejianlong.com/jlerp/st/do?pageId=stjj2201";

    ERPHttpClient hc4erp;

    String html;

    @Autowired
    public TransferMapper transferMapper;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void yuparse() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        QueryWrapper<Transfer> transferQueryWrapper = new QueryWrapper<>();
        transferQueryWrapper.like("loading_time", format);
        transferMapper.delete(transferQueryWrapper);
        loginERP();
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
                nvp.add(new BasicNameValuePair("productGenusId_qry",comID[i3]));
                nvp.add(new BasicNameValuePair("state_qry", a[i2]));
                nvp.add(new BasicNameValuePair("loadDateSt_qry", format));
                nvp.add(new BasicNameValuePair("loadDateEd_qry", format));
                nvp.add(new BasicNameValuePair("isHasException_qry", "false"));
                html = hc4erp.doPost(ERP_ENTRY, nvp, "GBK");
                Document doc = Jsoup.parse(html);
                //<input type="hidden" name="filePath" value="/file/sg/doc">
                Elements tr = doc.select("table[id=xpTable]").first().select("tr");
                if (tr.size()<=1){
                    continue;
                }
//                a4932e36962342638000d455b5a147f2
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
                    String urlssSsss = "http://erp.ejianlong.com/jlerp/st/do?pageId=stjj2202Print&flag=P&compId=HP021129192848107&st22Id=" + newstring.get(i);
                    String s = hc4erp.doGet(urlssSsss);
                    Document parse = Jsoup.parse(s);
                    /*从第五行开始*/
                    Elements table = parse.select("table").get(5).select("tr");
                    /*673eceaa08a64b448721df17d6cc430a*/
                    Elements table1 = parse.select("table").get(6).select("tr");
                    Elements table2 = parse.select("table").get(7).select("tr");
                    Transfer transfer = new Transfer();
                    String attr = table.get(0).select("td").get(1).text();
                    /*客户名称*/
                    transfer.setClient(attr);
//                    /*厂别*/
//                    String td = table.get(0).select("td").get(3).text();
//                    workSheet.setProcess(td);
                    /*装车作业单号*/
                    String td1 = table.get(1).select("td").get(1).text();
                    if (ObjectUtil.isEmpty(td1)){
                        continue;
                    }
                    transfer.setWorkSheetNum(td1);
                    /*移库计划单号*/
                    String td2 = table.get(1).select("td").get(3).text();
                    transfer.setTransferNum(td2);
                    /*产品分类*/
                    String td3 = table.get(1).select("td").get(5).text();
                    transfer.setProductType(td3);
                    /*发货通知单号*/
                    String td4 = table.get(2).select("td").get(1).text();
                    transfer.setShippingNoticeNum(td4);
                    /*标准全名*/
                    String td5 = table.get(2).select("td").get(3).text();
                    transfer.setStandard(td5);
                    /*提货经办人*/
                    String td6 = table.get(2).select("td").get(5).text();
                    transfer.setAgent(td6);
                    /*运输方式*/
                    String td7 = table.get(3).select("td").get(1).text();
                    transfer.setTransportationMode(td7);
                    /*车号*/
                    String td8 = table.get(3).select("td").get(3).text();
                    transfer.setCarNum(td8);
                    /*联系人/电话*/
                    String td9 = table.get(3).select("td").get(5).text();
                    transfer.setLinkmanTelepone(td9);
                    /*地点代码*/
                    String td10 = table.get(4).select("td").get(1).text();
                    transfer.setPlaceNum(td10);
                    /*专用线*/
                    String td11 = table.get(4).select("td").get(3).text();
                    transfer.setStoreroom(td11);
                    /*实际重量(吨)*/
                    String td12 = table.get(4).select("td").get(5).text();
                    transfer.setActualWeight(td12);
                    /*收货地址*/
                    String td121 = table.get(5).select("td").get(1).text();
                    transfer.setAddress(td121);
                    /*炉批号表中没有*/
                    /*牌号*/
                    String td13 = table1.get(1).select("td").get(2).text();
                    transfer.setSteel(td13);
                    /*规格*/
                    String td14 = table1.get(1).select("td").get(3).text();
                    transfer.setSpec(td14);
                    /*品质*/
                    String td15 = table1.get(1).select("td").get(4).text();
                    transfer.setQuality(td15);
                    /*理论重量*/
                    String td16 = table1.get(table1.size() - 2).select("td").get(1).text();
                    transfer.setTheoryWeight(td16);
                    /*件数*/
                    String td17 = table1.get(table1.size() - 2).select("td").get(2).text();
                    transfer.setBaleage(td17);
                    /*备注*/
                    String td18 = table1.get(table1.size() - 1).select("td").get(1).text();
                    transfer.setRemark(td18);
                    /*装车人*/
                    String td19 = table2.get(0).select("td").get(0).text();
                    transfer.setLoadingPeople(td19);
                    /*装车时间*/
                    String td20 = table2.get(0).select("td").get(1).text();
                    transfer.setLoadingTime(td20);
                    int insert = transferMapper.insert(transfer);
                }
            }
        }
    }

    /**
     * @param
     * @return 登入erp
     */
    public void loginERP() {
        hc4erp = new ERPHttpClient();
        // 其他位置不需要动,只修改账号密码
        hc4erp.loginERP("005247", "ming1108");
        // 选择抚顺公司别
        hc4erp.doGet("http://erp.ejianlong.com/km/ka/html/kajjGateway.html?compId=HP021129192848107");
    }

    /**
     * @param
     * @return 高线改盘圆
     */
    public void updatename() {
        List<Transfer> workSheets = transferMapper.selectList(null);
        ArrayList<Integer> idlist = new ArrayList<>();
        for (int i = 0; i < workSheets.size(); i++) {
            if ("高线".equals(workSheets.get(i).getProductType())) {
                idlist.add(workSheets.get(i).getId());
            }
        }
        for (int i = 0; i < idlist.size(); i++) {
            QueryWrapper<Transfer> wrapper = new QueryWrapper<>();
            wrapper.eq("id", idlist.get(i));
            Transfer workSheet = new Transfer();
            workSheet.setProductType("盘圆");
            transferMapper.update(workSheet, wrapper);
        }
    }

    /**
     * @param
     * @return 如果同一天存在兩條相同的装车作业单号, 则过滤掉第一条, 保留第二条并且存入新的数据库
     */
    public void filtr() {
        List<Transfer> workSheets = transferMapper.selectList(null);
        if (!ObjectUtils.isEmpty(workSheets)) {
            for (int i = 0; i < workSheets.size(); i++) {
                String workSheetNum = workSheets.get(i).getWorkSheetNum();
                String loadingTime = workSheets.get(i).getLoadingTime();
                if (ObjectUtils.isEmpty(loadingTime) || ObjectUtils.isEmpty(workSheetNum)) {
                    continue;
                }
                String substring = loadingTime.substring(5, 15);
                QueryWrapper<Transfer> wr = new QueryWrapper<>();
                wr.eq("work_sheet_num", workSheetNum);
                wr.like("loading_time", loadingTime);
                List<Transfer> workSheets1 = transferMapper.selectList(wr);
                if (workSheets1.size() <= 1) {
                    continue;
                }
                Integer id = workSheets.get(i).getId();
                QueryWrapper<Transfer> wid = new QueryWrapper<>();
                wid.eq("id", id);
                transferMapper.delete(wid);
                QueryWrapper<Transfer> workSheetQueryWrapper = new QueryWrapper<>();
                workSheetQueryWrapper.eq("work_sheet_num", workSheetNum);
                workSheetQueryWrapper.like("loading_time", loadingTime);
                List<Transfer> workSheets2 = transferMapper.selectList(workSheetQueryWrapper);
                if (!ObjectUtils.isEmpty(workSheets2)) {
                    Transfer workSheet02 = new Transfer();
                    workSheet02.setClient(workSheets2.get(0).getClient());
                    workSheet02.setWorkSheetNum(workSheets2.get(0).getWorkSheetNum());
                    workSheet02.setTransferNum(workSheets2.get(0).getTransferNum());
                    workSheet02.setProductType(workSheets2.get(0).getProductType());
                    workSheet02.setShippingNoticeNum(workSheets2.get(0).getShippingNoticeNum());
                    workSheet02.setStandard(workSheets2.get(0).getStandard());
                    workSheet02.setAgent(workSheets2.get(0).getAgent());
                    workSheet02.setTransportationMode(workSheets2.get(0).getTransportationMode());
                    workSheet02.setCarNum(workSheets2.get(0).getCarNum());
                    workSheet02.setLinkmanTelepone(workSheets2.get(0).getLinkmanTelepone());
                    workSheet02.setPlaceNum(workSheets2.get(0).getPlaceNum());
                    workSheet02.setStoreroom(workSheets2.get(0).getStoreroom());
                    workSheet02.setActualWeight(workSheets2.get(0).getActualWeight());
                    workSheet02.setAddress(workSheets2.get(0).getAddress());
                    workSheet02.setSteel(workSheets2.get(0).getSteel());
                    workSheet02.setSpec(workSheets2.get(0).getSpec());
                    workSheet02.setQuality(workSheets2.get(0).getQuality());
                    workSheet02.setTheoryWeight(workSheets2.get(0).getTheoryWeight());
                    workSheet02.setBaleage(workSheets2.get(0).getBaleage());
                    workSheet02.setRemark(workSheets2.get(0).getRemark());
                    workSheet02.setLoadingPeople(workSheets2.get(0).getLoadingPeople());
                    workSheet02.setLoadingTime(workSheets2.get(0).getLoadingTime());
                    /*现实际理论重量*/
//                    workSheet02.setActualWeight02(workSheets2.get(0).getActualWeight());
//                    workSheet02.setTheoryWeight02(workSheets2.get(0).getTheoryWeight());
//                    workSheet02.setBaleage02(workSheets2.get(0).getBaleage());
                }
            }
        }
    }
}
