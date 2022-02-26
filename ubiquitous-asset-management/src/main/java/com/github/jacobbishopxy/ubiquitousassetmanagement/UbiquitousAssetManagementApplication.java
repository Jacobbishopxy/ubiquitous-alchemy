/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.github.jacobbishopxy.ubiquitousassetmanagement")
@EnableJpaRepositories("com.github.jacobbishopxy.ubiquitousassetmanagement")
@EntityScan("com.github.jacobbishopxy.ubiquitousassetmanagement")
public class UbiquitousAssetManagementApplication {

	private static final Logger logger = LogManager.getLogger(UbiquitousAssetManagementApplication.class);

	public static void main(String[] args) {
		logger.info("Starting UbiquitousAssetManagementApplication...");
		SpringApplication.run(UbiquitousAssetManagementApplication.class, args);
	}

}
