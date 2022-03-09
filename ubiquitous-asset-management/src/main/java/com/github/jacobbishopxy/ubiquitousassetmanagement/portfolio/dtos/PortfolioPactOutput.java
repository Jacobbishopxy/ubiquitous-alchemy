/**
 * Created by Jacob Xie on 3/9/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.util.Date;

public record PortfolioPactOutput(String alias,
    String promoter,
    String industry,
    Date startDate,
    Date endDate,
    String description,
    Boolean isActive,
    Date lastUpdatedDate) {

}
