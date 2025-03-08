package net.ow.movie.theatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "net.ow.**")
@EnableFeignClients(basePackages = "net.ow.**")
public class MovieTheatreApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieTheatreApplication.class, args);
    }
}
