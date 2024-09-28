package com.yuunik.canalservice;

import com.yuunik.canalservice.client.CanalEduClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class CanalApplication implements CommandLineRunner {
    @Resource
    private CanalEduClient canalEduClient;

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(CanalApplication.class, args);
    }

    // 启动项目后，自动执行该方法
    @Override
    public void run(String... args) throws Exception {
        // 执行 canal 客户端监听
        canalEduClient.run();
    }
}
