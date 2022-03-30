/**
 * Created by Jacob Xie on 2/20/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dto;

import org.springframework.data.domain.Sort.Order;

public enum SortDirection {
  ASC, DESC;

  public Order getOrder(String property) {
    switch (this) {
      case ASC:
        return Order.asc(property);
      case DESC:
        return Order.desc(property);
      default:
        throw new IllegalArgumentException("Unknown sort direction: " + this);
    }
  }

  public static SortDirection fromString(String s) {
    if (s == null) {
      return null;
    }

    if (s.equalsIgnoreCase("asc")) {
      return ASC;
    } else if (s.equalsIgnoreCase("desc")) {
      return DESC;
    } else {
      throw new IllegalArgumentException("Invalid sort direction: " + s);
    }
  }
}
