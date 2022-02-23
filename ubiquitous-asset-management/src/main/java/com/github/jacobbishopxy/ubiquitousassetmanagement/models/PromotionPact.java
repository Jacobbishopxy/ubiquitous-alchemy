/**
 * Created by Jacob Xie on 2/22/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.dtos.DateRange;

import jakarta.persistence.*;

@Entity
@Table(name = "promotion_pact")
public class PromotionPact {

  @Id
  private String name;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false)
  private Date startDate;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false)
  private Date endDate;

  private String description;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotionPact")
  @JsonIgnore
  private List<PromotionRecord> promotionRecords;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotionPact")
  @JsonIgnore
  private List<PromotionStatistic> promotionStatistics;

  public PromotionPact() {
  }

  public PromotionPact(String name) {
    super();
    this.name = name;
  }

  public void validate() {
    if (startDate.after(endDate)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonIgnore
  public DateRange getDateRange() {
    return new DateRange(startDate, endDate);
  }

  public void setDateRange(DateRange dateRange) {
    this.startDate = dateRange.start();
    this.endDate = dateRange.end();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<PromotionRecord> getPromotionRecords() {
    return promotionRecords;
  }

  public void setPromotionRecords(List<PromotionRecord> promotionRecords) {
    this.promotionRecords = promotionRecords;
  }

  public List<PromotionStatistic> getPromotionStatistics() {
    return promotionStatistics;
  }

  public void setPromotionStatistics(List<PromotionStatistic> promotionStatistics) {
    this.promotionStatistics = promotionStatistics;
  }
}
