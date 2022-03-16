/**
 * Created by Jacob Xie on 3/16/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

public record PortfolioSettle(
    int pactId,
    LocalDate settlementDate) {

}
