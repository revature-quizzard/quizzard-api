package com.revature.quizzard;

import com.revature.quizzard.configs.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AppConfig.class})
public class Quizzard {

    public static void main(String[] args) {
        LogManager.getLogger().info("Log this please");
        System.out.println("Can you see me");
        System.out.println("I bet you can");
        SpringApplication.run(Quizzard.class, args);
    }
}
