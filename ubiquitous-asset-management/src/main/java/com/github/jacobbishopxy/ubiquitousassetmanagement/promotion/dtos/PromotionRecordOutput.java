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
		String promotionPactName,
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
		Float adjustFactor,
		Float earningsYield,
		Integer performanceScore,
		Boolean isArchived,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date createdAt,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date updatedAt) {

	/**
	 * fromPromotionRecord
	 * 
	 * According to GET methods, we will always get a full nested
	 * `PromotionRecordOutput` object.
	 * 
	 * @param promotionRecord
	 * @return
	 */
	public static PromotionRecordOutput fromPromotionRecord(
			PromotionRecord promotionRecord) {
		return new PromotionRecordOutput(
				promotionRecord.getId(),
				promotionRecord.getPromotionPact().getName(),
				promotionRecord.getPromoter().getNickname(),
				promotionRecord.getSymbol(),
				promotionRecord.getAbbreviation(),
				promotionRecord.getIndustry(),
				promotionRecord.getDirection(),
				promotionRecord.getOpenTime(),
				promotionRecord.getOpenPrice(),
				promotionRecord.getCloseTime(),
				promotionRecord.getClosePrice(),
				promotionRecord.getCurrency(),
				promotionRecord.getAdjustFactor(),
				promotionRecord.getEarningsYield(),
				promotionRecord.getPerformanceScore(),
				promotionRecord.getIsArchived(),
				promotionRecord.getCreatedAt(),
				promotionRecord.getUpdatedAt());
	}

	/**
	 * fromPromotionRecord
	 * 
	 * According to POST/PUT methods, we will only get the plain fields excluding
	 * the nested objects; hence, we need extra parameters to construct a
	 * `PromotionRecordOutput` object.
	 * 
	 * 
	 * @param promotionRecord
	 * @param promoterName
	 * @param promotionPactName
	 * @return
	 */
	public static PromotionRecordOutput fromPromotionRecord(
			PromotionRecord promotionRecord,
			String promotionPactName,
			String promoterName) {
		return new PromotionRecordOutput(
				promotionRecord.getId(),
				promotionPactName,
				promoterName,
				promotionRecord.getSymbol(),
				promotionRecord.getAbbreviation(),
				promotionRecord.getIndustry(),
				promotionRecord.getDirection(),
				promotionRecord.getOpenTime(),
				promotionRecord.getOpenPrice(),
				promotionRecord.getCloseTime(),
				promotionRecord.getClosePrice(),
				promotionRecord.getCurrency(),
				promotionRecord.getAdjustFactor(),
				promotionRecord.getEarningsYield(),
				promotionRecord.getPerformanceScore(),
				promotionRecord.getIsArchived(),
				promotionRecord.getCreatedAt(),
				promotionRecord.getUpdatedAt());
	}

}