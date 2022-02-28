/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models;

import javax.persistence.*;

// TODO:
// temporary disable table auto-generation
// @Entity
// @Table(name = "portfolio_performance")
public class PortfolioPerformance {
  @Id
  @Column(columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

}
