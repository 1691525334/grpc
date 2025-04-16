package com.example.grpcdemo;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NacosConfig {

    @Bean
    public NamingService namingService() throws Exception {
        // 根据 Nacos 的地址创建 NamingService 实例
        return NamingFactory.createNamingService("localhost:8849"); // 替换为你的 Nacos 地址
    }
}
