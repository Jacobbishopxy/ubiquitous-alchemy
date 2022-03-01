/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.fields.TradeDirection;

/**
 * PromotionRecordInput
 *
 * Used for creating or updating a promotion record
 * Notice:
 * - closeTime & closePrice is nullable
 * - abbreviation & industry should be auto-generated in the future
 */
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
		String currency,
		Float adjustFactorChange,
		String promotionPactName,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date createdAt,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date updatedAt) {

}
