package com.osp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrmProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmProcessApplication.class, args);
    }

}
