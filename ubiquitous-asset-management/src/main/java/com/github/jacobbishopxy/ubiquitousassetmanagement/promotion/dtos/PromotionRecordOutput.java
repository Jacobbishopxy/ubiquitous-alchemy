/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.PromotionRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.models.fields.TradeDirection;

/**
 * PromotionRecordOutput
 */
public record PromotionRecordOutput(
		Integer id,
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
		Float earningsYield,
		Integer performanceScore,
		Boolean isArchived,
		String promotionPactName,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date createdAt,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date updatedAt) {

	public static PromotionRecordOutput fromPromotionRecord(
			PromotionRecord promotionRecord) {
		return new PromotionRecordOutput(
				promotionRecord.getId(),
				promotionRecord.getPromoter().getNickname(),
				promotionRecord.getSymbol(),
				promotionRecord.getAbbreviation(),
				promotionRecord.getIndustry(),
				promotionRecord.getDirection(),
				promotionRecord.getOpenTime(),
				promotionRecord.getOpenPrice(),
				promotionRecord.getCloseTime(),
				promotionRecord.getClosePrice(),
				promotionRecord.getAdjustFactor(),
				promotionRecord.getEarningsYield(),
				promotionRecord.getPerformanceScore(),
				promotionRecord.getIsArchived(),
				promotionRecord.getPromotionPact().getName(),
				promotionRecord.getCreatedAt(),
				promotionRecord.getUpdatedAt());
	}

	public static PromotionRecordOutput fromPromotionRecord(
			PromotionRecord promotionRecord,
			String promoterName,
			String promotionPactName) {
		return new PromotionRecordOutput(
				promotionRecord.getId(),
				promoterName,
				promotionRecord.getSymbol(),
				promotionRecord.getAbbreviation(),
				promotionRecord.getIndustry(),
				promotionRecord.getDirection(),
				promotionRecord.getOpenTime(),
				promotionRecord.getOpenPrice(),
				promotionRecord.getCloseTime(),
				promotionRecord.getClosePrice(),
				promotionRecord.getAdjustFactor(),
				promotionRecord.getEarningsYield(),
				promotionRecord.getPerformanceScore(),
				promotionRecord.getIsArchived(),
				promotionPactName,
				promotionRecord.getCreatedAt(),
				promotionRecord.getUpdatedAt());
	}

}
