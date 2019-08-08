package com.eks;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing//将表中数据的创建时间、修改时间交给spring
@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(Main.class);
		//解决java.awt.HeadlessException。Spring Boot 应用默认情况下运行在headless模式,意味着运行在没有GUI的服务器或者其他环境,不设置的话,任何AWT GUI元素都将会抛出java.awt.HeadlessException异常
		//且需要在VM的options加上(-Djava.awt.headless=false)
		springApplicationBuilder.headless(false);
		springApplicationBuilder.web(WebApplicationType.SERVLET);
		springApplicationBuilder.run(args);//将表中数据的创建时间、修改时间交给spring
	}
}
