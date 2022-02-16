/**
 * Created by Jacob Xie on 2/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.dtos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;

public record DateRange(Date start, Date end) {

  private static String format = "yyyy-MM-dd";
  private static Pattern pattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}):(\\d{4}-\\d{2}-\\d{2})$");

  public static DateRange fromString(String s) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Matcher matcher = pattern.matcher(s);

    if (matcher.matches()) {
      return new DateRange(sdf.parse(matcher.group(1)), sdf.parse(matcher.group(2)));
    } else {
      throw new IllegalArgumentException("Invalid date range format: " + s);
    }
  }
}
