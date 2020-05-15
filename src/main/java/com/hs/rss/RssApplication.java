package com.hs.rss;

import com.hs.rss.vo.ENV;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.hs.rss",exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.hs.rss.mapper")
public class RssApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssApplication.class, args);
		new ENV();
	}

}
