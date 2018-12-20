package com.charles.SpringBootGuide;

import com.charles.imsdk.ImClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootGuideApplication {

	public static void main(String[] args) {
		try {
			ImClient.getInstance().login("localhost", 30000);
		} catch (InterruptedException e) {
			System.out.println(e.getCause());
		}

		SpringApplication.run(SpringBootGuideApplication.class, args);
	}
}
