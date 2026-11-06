package dev.eder.qhatu_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // activa los @Scheduled (reenvío al facturador)
public class QhatuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QhatuApiApplication.class, args);
	}

}
