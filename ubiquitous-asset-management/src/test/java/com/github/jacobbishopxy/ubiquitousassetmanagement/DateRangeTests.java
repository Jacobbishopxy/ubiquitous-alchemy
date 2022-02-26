/**
 * Created by Jacob Xie on 2/22/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.jacobbishopxy.ubiquitousassetmanagement.promotion.dtos.DateRange;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DateRangeTests {

  @Test
  void contextLoads() throws ParseException {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date start = sdf.parse("2021-01-01");
    Date end = sdf.parse("2020-01-01");

    assertThrows(IllegalArgumentException.class, () -> new DateRange(start, end));

    assertThrows(NullPointerException.class, () -> new DateRange(null, end));

  }

}
