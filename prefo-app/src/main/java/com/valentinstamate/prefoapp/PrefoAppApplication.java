package com.valentinstamate.prefoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class PrefoAppApplication {

	public static void main(String[] args) {
		var application = new SpringApplication(PrefoAppApplication.class);
		application.run(args);
	}

}
