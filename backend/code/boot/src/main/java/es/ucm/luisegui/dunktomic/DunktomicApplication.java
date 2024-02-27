package es.ucm.luisegui.dunktomic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"es.ucm.luisegui"})
public class DunktomicApplication {

    public static void main(String[] args) {
        SpringApplication.run(DunktomicApplication.class, args);
    }
}
