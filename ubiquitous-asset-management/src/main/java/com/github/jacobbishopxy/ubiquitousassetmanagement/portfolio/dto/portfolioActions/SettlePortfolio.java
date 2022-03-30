/**
 * Created by Jacob Xie on 3/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.portfolioActions;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

@Schema(name = "PortfolioActionSettle", description = "Settle portfolio")
public record SettlePortfolio(
		Long pactId,
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate settlementDate) {

}
