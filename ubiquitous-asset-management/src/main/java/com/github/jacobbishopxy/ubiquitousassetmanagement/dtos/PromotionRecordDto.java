/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.Direction;
import com.github.jacobbishopxy.ubiquitousassetmanagement.models.PromotionRecord;

public record PromotionRecordDto(
		Integer id,
		String promoterName,
		String symbol,
		String abbreviation,
		String industry,
		Direction direction,
		@JsonFormat(pattern = Constants.DATE_FORMAT) Date openTime,
		Float openPrice,
		@JsonFormat(pattern = Constants.DATE_FORMAT) Date closeTime,
		Float closePrice,
		Float earningsYield,
		Float score,
		Boolean isArchived,
		Integer promotionPactId,
		@JsonFormat(pattern = Constants.DATE_FORMAT) Date createdAt,
		@JsonFormat(pattern = Constants.DATE_FORMAT) Date updatedAt) {

	public static PromotionRecordDto fromPromotionRecord(PromotionRecord promotionRecord) {
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
				promotionRecord.getPromotionPact().getId(),
				promotionRecord.getCreatedAt(),
				promotionRecord.getUpdatedAt());
	}

	public static PromotionRecordDto fromPromotionRecord(PromotionRecord promotionRecord, String name,
			Integer promotionPactId) {
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
				promotionPactId,
				promotionRecord.getCreatedAt(),
				promotionRecord.getUpdatedAt());
	}

}
