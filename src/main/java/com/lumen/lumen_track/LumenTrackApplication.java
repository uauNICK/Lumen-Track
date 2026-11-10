package com.lumen.lumen_track;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "Lumen Track API",
				version = "1.0.0",
				description = "API REST para gerenciamento de atividades."
		)
)
@SpringBootApplication
public class LumenTrackApplication {

	public static void main(String[] args) {

		SpringApplication.run(LumenTrackApplication.class, args);
	}

}
