/**
 * Created by Jacob Xie on 2/24/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.models.fields;

public enum PerformanceScore {
  EPIC_FAILURE,
  FAILURE,
  PASS,
  SUCCESS,
  EXCELLENCE;

  public int score() {
    switch (this) {
      case EPIC_FAILURE:
        return -20;
      case FAILURE:
        return -10;
      case PASS:
        return 10;
      case SUCCESS:
        return 20;
      case EXCELLENCE:
        return 40;
      default:
        return 0;
    }
  }

  public static PerformanceScore fromEarningsYield(Float earningsYield) {
    if (earningsYield == null) {
      return null;
    } else if (earningsYield < -.25) {
      return EPIC_FAILURE;
    } else if (earningsYield < -.1) {
      return FAILURE;
    } else if (earningsYield < .15) {
      return PASS;
    } else if (earningsYield <= .25) {
      return SUCCESS;
    } else {
      return EXCELLENCE;
    }
  }
}
