/**
 * Created by Jacob Xie on 3/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos;

import java.time.LocalTime;

public record AdjustmentInfoPatch(
    LocalTime adjustTime,
    String description) {

}
