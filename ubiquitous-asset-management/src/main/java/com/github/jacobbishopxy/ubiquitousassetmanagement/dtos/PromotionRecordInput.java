/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.fields.TradeDirection;

public record PromotionRecordInput(
		String promoter,
		String symbol,
		String abbreviation,
		String industry,
		TradeDirection direction,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date openTime,
		Float openPrice,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date closeTime,
		Float closePrice,
		Float adjustFactor,
		String promotionPactName,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date createdAt,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date updatedAt) {

}
