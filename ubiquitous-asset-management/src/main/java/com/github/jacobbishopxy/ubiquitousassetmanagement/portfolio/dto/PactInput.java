/**
 * Created by Jacob Xie on 3/9/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Schema(name = "PortfolioPactInput", description = "Pact input")
public record PactInput(
		String alias,
		String promoterName,
		String industryName,
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate startDate,
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate endDate,
		String description) {

}
