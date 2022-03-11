/**
 * Created by Jacob Xie on 3/9/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

public record PactInput(
                String alias,
                String promoter,
                String industry,
                LocalDate startDate,
                LocalDate endDate,
                String description) {

}
