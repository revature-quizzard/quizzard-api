package com.revature.quizzard;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Quizzard implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Quizzard.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
    }

}
