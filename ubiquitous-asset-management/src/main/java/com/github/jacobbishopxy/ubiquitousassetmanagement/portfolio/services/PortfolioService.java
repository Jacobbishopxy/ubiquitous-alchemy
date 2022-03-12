/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PortfolioService
 * 
 * Full commands:
 * 1. Settle an existing portfolio. AdjustmentRecord/AdjustmentInfo/Performance
 * is created.
 * 2. Cancel a portfolio settle. AdjustmentRecord/AdjustmentInfo/Performance is
 * deleted.
 * 3. Adjust an existing portfolio, which needs to be settled first (check
 * AdjustmentRecord's date and version). This operation will call `settle`
 * method automatically.
 * 4. Cancel an adjustment. Unsettle portfolio.
 */
@Service
public class PortfolioService {

  @Autowired
  private PactService pactService;

  @Autowired
  private AdjustmentRecordService adjustmentRecordService;

  @Autowired
  private AdjustmentInfoService adjustmentInfoService;

  @Autowired
  private BenchmarkService benchmarkService;

  @Autowired
  private ConstituentService constituentService;

}
