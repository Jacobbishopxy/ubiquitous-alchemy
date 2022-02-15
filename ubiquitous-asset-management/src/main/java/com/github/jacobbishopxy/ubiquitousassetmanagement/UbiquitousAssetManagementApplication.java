package com.github.jacobbishopxy.ubiquitousassetmanagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UbiquitousAssetManagementApplication {

	private static final Logger logger = LogManager.getLogger(UbiquitousAssetManagementApplication.class);

	public static void main(String[] args) {
		logger.info("Starting UbiquitousAssetManagementApplication...");
		SpringApplication.run(UbiquitousAssetManagementApplication.class, args);
	}

}
