package br.com.integracaoFipe.integracaoFipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IntegracaoFipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegracaoFipeApplication.class, args);
	}

}