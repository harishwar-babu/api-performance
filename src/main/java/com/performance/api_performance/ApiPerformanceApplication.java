package com.performance.api_performance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiPerformanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPerformanceApplication.class, args);
	}

}
