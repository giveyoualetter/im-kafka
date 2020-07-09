package com.zsc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/25 9:17
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class BootStrapApplication {
    private static final Logger log = LoggerFactory.getLogger(BootStrapApplication.class);
    public static void main(String[] args){
        SpringApplication.run(BootStrapApplication.class,args);
    }
}
