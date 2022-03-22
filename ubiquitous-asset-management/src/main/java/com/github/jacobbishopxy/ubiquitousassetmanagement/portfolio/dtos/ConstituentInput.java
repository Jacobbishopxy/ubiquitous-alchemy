/**
 * Created by Jacob Xie on 3/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;

// import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

// @Schema(name = "PortfolioConstituentInput", description = "Constituent input")
public record ConstituentInput(
		Long adjustmentRecordId,
		@JsonFormat(pattern = Constants.DATE_FORMAT) LocalDate adjustDate,
		String symbol,
		String abbreviation,
		Float adjustDatePrice,
		Float currentPrice,
		Float adjustDateFactor,
		Float currentFactor,
		Float weight,
		Float pbpe,
		Float marketValue) {

	public static Constituent intoConstituent(ConstituentInput dto) {
		AdjustmentRecord ar = new AdjustmentRecord();
		ar.setId(dto.adjustmentRecordId());

		Float earningsYield = 0f;
		Float pctChg = 0f;

		// temporary solution (under no automatic calculation situation)
		Float adjDateFactor = dto.adjustDateFactor() == null ? 1f : dto.adjustDateFactor();
		if (dto.adjustDatePrice() != null &&
				dto.currentPrice() != null &&
				dto.currentFactor() != null) {
			Float adjChg = dto.currentFactor() / adjDateFactor;
			pctChg = adjChg * dto.currentPrice() / dto.adjustDatePrice();
			earningsYield = pctChg - 1;
		}
		// since we not yet have the totalPctChg, saving expansionRate in order to
		// calculate the currentWeight afterwards
		Float expansionRate = dto.weight() * pctChg;

		return new Constituent(
				ar,
				dto.adjustDate(),
				dto.symbol(),
				dto.abbreviation(),
				dto.adjustDatePrice(),
				dto.currentPrice(),
				adjDateFactor,
				dto.currentFactor(),
				dto.weight(),
				expansionRate, // currentWeight = expansionRate / sum of all expansionRates
				dto.pbpe(),
				dto.marketValue(),
				earningsYield);
	}

}
