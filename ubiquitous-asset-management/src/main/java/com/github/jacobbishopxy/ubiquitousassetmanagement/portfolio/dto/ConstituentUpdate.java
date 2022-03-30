/**
 * Created by Jacob Xie on 3/25/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PortfolioConstituentUpdate", description = "Constituent update")
public record ConstituentUpdate(
		Float currentFactor,
		Float currentPrice,
		Float pbpe,
		Float marketValue) {

}
