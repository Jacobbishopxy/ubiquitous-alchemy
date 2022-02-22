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
public class PromotionPact {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false)
  private Date startDate;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = Constants.DATE_FORMAT)
  @Column(nullable = false)
  private Date endDate;

  private String description;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion_record")
  @JsonIgnore
  private List<PromotionRecord> promotionRecords;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion_statistic")
  @JsonIgnore
  private List<PromotionStatistic> promotionStatistics;

  public PromotionPact() {
  }

  public PromotionPact(Integer id) {
    super();
    this.id = id;
  }

  public PromotionPact(DateRange dateRange, String description) {
    super();
    this.startDate = dateRange.start();
    this.endDate = dateRange.end();
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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
