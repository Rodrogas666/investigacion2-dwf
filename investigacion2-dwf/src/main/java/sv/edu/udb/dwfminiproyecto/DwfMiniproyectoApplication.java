package sv.edu.udb.dwfminiproyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DwfMiniproyectoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DwfMiniproyectoApplication.class, args);
    }
}
