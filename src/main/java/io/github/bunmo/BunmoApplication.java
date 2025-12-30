package io.github.bunmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BunmoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BunmoApplication.class, args);
    }

}
