/**
 * Created by Jacob Xie on 4/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.github.jacobbishopxy.ubiquitousauth")
@EntityScan("com.github.jacobbishopxy.ubiquitousauth")
public class UbiquitousAuthApplication {

	private static final Logger logger = LogManager.getLogger(UbiquitousAuthApplication.class);

	public static void main(String[] args) {
		logger.info("Starting UbiquitousAuthApplication...");

		SpringApplication.run(UbiquitousAuthApplication.class, args);
	}

}
