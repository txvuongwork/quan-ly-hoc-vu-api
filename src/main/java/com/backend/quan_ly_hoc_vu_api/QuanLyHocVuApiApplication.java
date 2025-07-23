package com.backend.quan_ly_hoc_vu_api;

import com.backend.quan_ly_hoc_vu_api.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
@EnableScheduling
public class QuanLyHocVuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuanLyHocVuApiApplication.class, args);
	}

}
