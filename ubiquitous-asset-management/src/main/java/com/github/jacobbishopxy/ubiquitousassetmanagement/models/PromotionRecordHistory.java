/**
 * Created by Jacob Xie on 2/24/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;

import jakarta.persistence.*;

@Entity
@Table(name = "promotion_record_history")
public class PromotionRecordHistory {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // TODO:
  // @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "promoter_email")
  // @JsonIgnore
  // private PromotionRecord promotionRecord;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = Constants.TIME_FORMAT)
  private Date date;

  private String description;

  public PromotionRecordHistory() {
  }

  public PromotionRecordHistory(Date date, String description) {
    super();
    this.date = date;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
