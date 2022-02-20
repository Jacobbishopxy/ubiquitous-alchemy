/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.dtos;

import java.util.regex.*;

public record IntegerRange(Float start, Float end) {

  private static Pattern pattern = Pattern.compile("^(\\d+):(\\d+)$");

  public static IntegerRange fromString(String s) {
    if (s == null) {
      return null;
    }

    Matcher matcher = pattern.matcher(s);
    if (matcher.matches()) {
      return new IntegerRange(Float.parseFloat(matcher.group(1)), Float.parseFloat(matcher.group(2)));
    } else {
      throw new IllegalArgumentException("Invalid integer range format: " + s);
    }
  }
}
