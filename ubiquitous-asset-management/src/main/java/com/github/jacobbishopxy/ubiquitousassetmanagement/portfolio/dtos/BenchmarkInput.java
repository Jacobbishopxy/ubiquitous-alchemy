/**
 * Created by Jacob Xie on 3/14/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalDate;

public record BenchmarkInput(
        int adjustmentRecordId,
        LocalDate adjustDate,
        String benchmarkName,
        Float percentageChange,
        Float weight) {

}
