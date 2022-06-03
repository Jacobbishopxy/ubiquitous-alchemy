/**
 * Created by Jacob Xie on 6/1/2022.
 */

package com.github.jacobbishopxy.ubiquitousresourcecentre;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@EntityScan("com.github.jacobbishopxy.ubiquitousassetmanagement")
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class UbiquitousResourceCentreApplication {

	private static final Logger logger = LogManager.getLogger(UbiquitousResourceCentreApplication.class);

	public static void main(String[] args) {
		logger.info("Starting UbiquitousResourceCentreApplication...");
		SpringApplication.run(UbiquitousResourceCentreApplication.class, args);
	}

}
