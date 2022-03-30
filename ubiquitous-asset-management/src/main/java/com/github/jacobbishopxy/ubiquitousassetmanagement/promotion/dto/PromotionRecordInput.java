/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.domain.obj.TradeDirection;

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
		@JsonFormat(pattern = Constants.DATETIME_FORMAT) Date openTime,
		Float openPrice,
		@JsonFormat(pattern = Constants.DATETIME_FORMAT) Date closeTime,
		Float closePrice,
		String currency,
		Float openTimeAdjustFactor,
		Float closeTimeAdjustFactor,
		String promotionPactName,
		@JsonFormat(pattern = Constants.DATETIME_FORMAT) Date createdAt,
		@JsonFormat(pattern = Constants.DATETIME_FORMAT) Date updatedAt) {

}
