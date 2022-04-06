package com.parseweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling /*发现@Scheduled注解,用来执行定时任务*/
public class ParseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParseWebApplication.class, args);
    }

}
