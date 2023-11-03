package in.gov.abdm.hip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableWebFlux
@EnableReactiveFeignClients
public class HIPApplication {
    public static void main(String[] args) {
        SpringApplication.run(HIPApplication.class, args);
    }
}