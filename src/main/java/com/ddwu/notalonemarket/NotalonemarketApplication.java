package com.ddwu.notalonemarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.ddwu.notalonemarket.mapper")
public class NotalonemarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotalonemarketApplication.class, args);
	}
}