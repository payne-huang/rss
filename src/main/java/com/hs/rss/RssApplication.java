package com.hs.rss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.hs.rss.mapper")
public class RssApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssApplication.class, args);
	}

}
