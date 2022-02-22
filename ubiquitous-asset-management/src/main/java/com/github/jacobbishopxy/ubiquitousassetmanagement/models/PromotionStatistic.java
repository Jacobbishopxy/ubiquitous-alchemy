/**
 * Created by Jacob Xie on 2/22/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models;

import jakarta.persistence.*;

@Entity
public class PromotionStatistic {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promoter_email")
  private Promoter promoter;

  @Column(nullable = false)
  private Float totalScore;

  @Column(nullable = false)
  private Integer promotionCount;

  @Column(nullable = false)
  private Float baseScore;

  @Column(nullable = false)
  private Float performScore;

  @Column(nullable = false)
  private Integer promotionSuccessCount;

  @Column(nullable = false)
  private Integer promotionFailureCount;

  @Column(nullable = false)
  private Float successRate;

  // TODO: OneToOne PromotionPact
}
