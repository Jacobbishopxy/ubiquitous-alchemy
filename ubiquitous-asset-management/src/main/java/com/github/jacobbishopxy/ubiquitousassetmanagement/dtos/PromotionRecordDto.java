/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.TradeDirection;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;

public record PromotionRecordDto(
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
		Float earningsYield,
		Float score,
		Boolean isArchived,
		String promotionPactName,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date createdAt,
		@JsonFormat(pattern = Constants.TIME_FORMAT) Date updatedAt) {

	public static PromotionRecordDto fromPromotionRecord(
			PromotionRecord promotionRecord) {
		return new PromotionRecordDto(
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
				promotionRecord.getEarningsYield(),
				promotionRecord.getScore(),
				promotionRecord.getIsArchived(),
				promotionRecord.getPromotionPact().getName(),
				promotionRecord.getCreatedAt(),
				promotionRecord.getUpdatedAt());
	}

	public static PromotionRecordDto fromPromotionRecord(
			PromotionRecord promotionRecord,
			String name,
			String promotionPactName) {
		return new PromotionRecordDto(
				promotionRecord.getId(),
				name,
				promotionRecord.getSymbol(),
				promotionRecord.getAbbreviation(),
				promotionRecord.getIndustry(),
				promotionRecord.getDirection(),
				promotionRecord.getOpenTime(),
				promotionRecord.getOpenPrice(),
				promotionRecord.getCloseTime(),
				promotionRecord.getClosePrice(),
				promotionRecord.getEarningsYield(),
				promotionRecord.getScore(),
				promotionRecord.getIsArchived(),
				promotionPactName,
				promotionRecord.getCreatedAt(),
				promotionRecord.getUpdatedAt());
	}

}
