package es.ucm.luisegui.dunktomic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"es.ucm.luisegui.dunktomic"})
public class DunktomicApplication {

    public static void main(String[] args) {
        SpringApplication.run(DunktomicApplication.class, args);
    }
}
