/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Constants
 *
 * Used for Promotion business logic.
 */
public class Constants {

  public static final String API_VERSION = "v1";
  public static final String API_PROMOTION = "/promotion";
  public static final String API_PORTFOLIO = "/portfolio";

  public static final String DATE_FORMAT = "yyyy-MM-dd";
  public static final String TIME_FORMAT = "HH:mm:ss";
  public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static final String dateToString(Date date) {
    return new SimpleDateFormat(DATE_FORMAT).format(date);
  }

  public static final String datetimeToString(Date date) {
    return new SimpleDateFormat(DATETIME_FORMAT).format(date);
  }
}
