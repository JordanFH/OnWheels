package org.onwheels.springcloud.msvc.autos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcAutosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAutosApplication.class, args);
	}

}
