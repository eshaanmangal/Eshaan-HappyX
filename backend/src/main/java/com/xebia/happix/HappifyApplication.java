package com.xebia.happix;

import com.google.common.collect.Sets;
import com.xebia.happix.model.EmailTemplate;
import com.xebia.happix.repository.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class HappifyApplication {


    public static void main(String[] args) {
        SpringApplication.run(HappifyApplication.class, args);
    }

}
