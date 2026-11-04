package com.tecsup.recibo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ReciboServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReciboServiceApplication.class, args);
    }

    // se usa para consultar a cliente-service y servicio-service por HTTP
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
