package com.amanda.MedicationTracker;

import com.amanda.MedicationTracker.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan("com.amanda.MedicationTracker")
@Import(AppConfig.class)
public class MedicationTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicationTrackerApplication.class, args);
	}

}
