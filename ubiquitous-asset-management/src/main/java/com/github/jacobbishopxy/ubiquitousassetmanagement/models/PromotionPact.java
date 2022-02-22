/**
 * Created by Jacob Xie on 2/22/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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

  public PromotionPact() {
  }

  public PromotionPact(DateRange dateRange, String description) {
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
}
