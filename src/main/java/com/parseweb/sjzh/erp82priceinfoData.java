package com.parseweb.sjzh;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.parseweb.erp82.entity.Priceinfodata;
import com.parseweb.erp82.mapper.PriceinfodataMapper;
import com.parseweb.util.ERPHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

@Component
public class erp82priceinfoData {
    @Autowired PriceinfodataMapper priceinfodataMapper;

    ERPHttpClient hc4erp;

    @Scheduled(cron = "0 0 1 * * ?")
    public void gilcai() {
        hc4erp = new ERPHttpClient();
        String yesterday = "";
        //获取昨天的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        System.out.println(yesterday);
        String fwurl = "http://erp.ejianlong.com/jlerp/ms/MActionServlet/com.jl.ms.servlet.OtherSysServlet/getMrPriceInfoData?Content-Type=application/json&date=" + "" + yesterday + "" + "&compId=2df22a6f57fb4a6fbc83503189845932";
        String s = hc4erp.doGet(fwurl);
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONArray priceInfoData = jsonObject.getJSONArray("priceInfoData");
        String matrlName = "";
        JSONObject jsonObject1 = new JSONObject();
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < priceInfoData.size(); i++) {
            Priceinfodata priceinfodata = new Priceinfodata();
            jsonObject1 = JSONObject.parseObject(priceInfoData.get(i).toString());
            priceinfodata.setMatrlname(jsonObject1.getString("matrlName"));
            priceinfodata.setMatrlno(jsonObject1.getString("matrlNo"));
            priceinfodata.setPrice(df.format(new BigDecimal(jsonObject1.getString("price"))));
            if (!ObjectUtils.isEmpty(jsonObject1.getString("vendorName"))) {
                priceinfodata.setVendorname(jsonObject1.getString("vendorName"));
            }
            priceinfodata.setDate(LocalDate.parse(yesterday));
            priceinfodataMapper.insert(priceinfodata);
        }
    }

}
