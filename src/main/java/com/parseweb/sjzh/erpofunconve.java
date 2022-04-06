package com.parseweb.sjzh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.parseweb.lzih.entity.Parsecz;
import com.parseweb.lzih.entity.Unconerp;
import com.parseweb.lzih.mapper.ParseczMapper;
import com.parseweb.lzih.mapper.UnconerpMapper;
import com.parseweb.uncon.entity.Unconventionality;
import com.parseweb.uncon.mapper.UnconventionalityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class erpofunconve {
    @Autowired
    public UnconventionalityMapper unconventionalityMapper;
    @Autowired
    public ParseczMapper parseczMapper;
    @Autowired
    public UnconerpMapper unconerpMapper;

    @Scheduled(cron = "0 0 23 * * ?")
    public void unconoferp() {
        unconerpMapper.delete(null);
        QueryWrapper<Unconventionality> uqw = new QueryWrapper<>();
        uqw.like("number", "fs");
        uqw.groupBy("number");
        List<Unconventionality> unconventionalities = unconventionalityMapper.selectList(uqw);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < unconventionalities.size(); i++) {
            if (unconventionalities.get(i).getNumber().indexOf("-") != -1 ||
                unconventionalities.get(i).getNumber().indexOf("中") != -1 ||
                unconventionalities.get(i).getNumber().indexOf("下") != -1){
                continue;
            }
            strings.add(unconventionalities.get(i).getNumber());
        }

        for (int i = 0; i < strings.size(); i++) {
            QueryWrapper<Unconventionality> qw = new QueryWrapper<>();
            qw.eq("number",strings.get(i));
            List<Unconventionality> ul = unconventionalityMapper.selectList(qw);
            QueryWrapper<Parsecz> pqw = new QueryWrapper<>();
            pqw.eq("ph",strings.get(i));
            List<Parsecz> parseczs = parseczMapper.selectList(pqw);
            if (ObjectUtils.isEmpty(parseczs)){
                continue;
            }
            Parsecz parsecz = parseczs.get(parseczs.size() - 1);
            for (int i1 = 0; i1 < ul.size(); i1++) {
                Unconerp unconerp = new Unconerp();
                unconerp.setNumber(ul.get(i1).getNumber());
                unconerp.setItem(ul.get(i1).getItem());
                unconerp.setPrice(ul.get(i1).getPrice());
                unconerp.setDate(ul.get(i1).getDate());
                unconerp.setGysmc(parsecz.getGysmc());
                unconerp.setYpmc(parsecz.getYlmc());
                unconerpMapper.insert(unconerp);
            }
        }
    }
}
