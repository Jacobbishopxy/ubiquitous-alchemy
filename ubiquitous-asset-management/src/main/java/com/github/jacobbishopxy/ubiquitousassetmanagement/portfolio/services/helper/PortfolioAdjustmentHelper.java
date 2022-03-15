/**
 * Created by Jacob Xie on 3/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper;

import java.util.List;
import java.util.Set;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.google.common.collect.Sets;

public class PortfolioAdjustmentHelper {

  private static <T> Set<T> intersection(Set<T> pre, Set<T> cur) {
    Set<T> intersection = Sets.newHashSet(pre);
    intersection.retainAll(cur);
    return intersection;
  }

  private static <T> Set<T> difference(Set<T> pre, Set<T> cur) {
    Set<T> difference = Sets.newHashSet(pre);
    difference.removeAll(cur);
    return difference;
  }

  public static List<AdjustmentInfo> adjust(
      List<Constituent> preCons,
      List<Constituent> curCons) {

    Set<Constituent> preConsSet = Sets.newHashSet(preCons);
    Set<Constituent> curConsSet = Sets.newHashSet(curCons);

    Set<Constituent> remaining = intersection(preConsSet, curConsSet);
    Set<Constituent> added = difference(curConsSet, preConsSet);
    Set<Constituent> removed = difference(preConsSet, curConsSet);

    // TODO:

    return null;
  }

}
