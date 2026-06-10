package com.tyrservices.agronetb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AgroNetBApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgroNetBApplication.class, args);
    }

}
