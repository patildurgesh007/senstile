package com.senstile;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrdersDeliverySystemApplication {

	@Autowired
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(OrdersDeliverySystemApplication.class, args);
	}
}
