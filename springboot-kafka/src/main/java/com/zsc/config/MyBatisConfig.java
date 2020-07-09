package com.zsc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/16 23:35
 */

/**
 * 扫描Mapper接口所在包使用MapperScan，且扫描的是包名
 */
@Configuration
@MapperScan({"com.zsc.dao","com.zsc.mbg.mapper"})
public class MyBatisConfig {
}
