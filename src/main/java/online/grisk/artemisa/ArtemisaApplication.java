package online.grisk.zeus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("integration.cfg.xml")
public class ZeusApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeusApplication.class, args);
    }

}
