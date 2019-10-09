package com.example.stock;

import java.util.Properties;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class StockReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockReaderApplication.class, args);
	}
	
	@Bean
	 public JavaMailSender javaMailService() {
	        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

	        javaMailSender.setHost("smtp.gmail.com");
	        javaMailSender.setPort(587);

	        javaMailSender.setJavaMailProperties(getMailProperties());
	        javaMailSender.setUsername("nitinpatel244@gmail.com");
	        javaMailSender.setPassword("nitin@1234");

	        return javaMailSender;
	    }

	    private Properties getMailProperties() {
	        Properties properties = new Properties();
	        properties.setProperty("mail.transport.protocol", "smtp");
	        properties.setProperty("mail.smtp.auth", "true");
	        properties.setProperty("mail.smtp.starttls.enable", "true");
	        properties.setProperty("mail.debug", "true");
	        properties.setProperty("mail.smtp.ssl.enable","true");
	        properties.setProperty("mail.test-connection","true");
	        return properties;
	    }
	    
	    @Bean("threadPoolTaskExecutor")
	    public Executor asyncExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(2);
	        executor.setMaxPoolSize(2);
	        executor.setQueueCapacity(500);
	        executor.setThreadNamePrefix("JDAsync-");
	        executor.initialize();
	        return executor;
	    }
}
