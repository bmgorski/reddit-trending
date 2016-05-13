package edu.stthomas;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static final String FILE_FOLDER = "data";
	
	public static void main(String[] args) {		
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	CommandLineRunner init() {
		return (String[] args) -> {
			new File(FILE_FOLDER).mkdir();
		};
	}
}
