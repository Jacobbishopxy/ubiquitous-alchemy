/**
 * Created by Jacob Xie on 3/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.fields.AdjustmentOperation;
import com.google.common.collect.HashBiMap;
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

  // IMPORTANT:
  // If preCons and curCons are the same (same constituents with unchanged static
  // weight), then AdjustmentInfos will be empty.
  public static List<AdjustmentInfo> adjust(
      List<Constituent> preCons,
      List<Constituent> curCons) {

    Map<Integer, Constituent> preConsMap = HashBiMap
        .create(preCons
            .stream()
            .collect(Collectors.toMap(Constituent::getId, c -> c)));
    Map<Integer, Constituent> curConsMap = HashBiMap
        .create(curCons
            .stream()
            .collect(Collectors.toMap(Constituent::getId, c -> c)));

    Set<Integer> preConsIds = Sets.newHashSet(preConsMap.keySet());
    Set<Integer> curConsIds = Sets.newHashSet(curConsMap.keySet());

    // remaining: if the current static weight is less than the previous one, mark
    // the constituent as `Decrease`; otherwise, mark it as `Increase`.
    Set<Integer> remainingIds = intersection(preConsIds, curConsIds);
    // added: mark it as `Join`.
    Set<Integer> addedIds = difference(curConsIds, preConsIds);
    // popped: mark it as `Leave`.
    Set<Integer> poppedIds = difference(preConsIds, curConsIds);

    List<AdjustmentInfo> aisRemaining = remainingIds
        .stream()
        .map(i -> {
          Constituent preC = preConsMap.get(i);
          Constituent curC = curConsMap.get(i);

          AdjustmentInfo ai = new AdjustmentInfo();
          ai.setAdjustmentRecord(preC.getAdjustmentRecord());
          ai.setAdjustTime(LocalTime.now());
          ai.setSymbol(preC.getSymbol());
          ai.setAbbreviation(preC.getAbbreviation());
          // operation based on the static weight's change
          AdjustmentOperation ao = AdjustmentOperation.Unchanged;
          if (preC.getStaticWeight() < curC.getStaticWeight()) {
            ao = AdjustmentOperation.Decrease;
          } else if (preC.getStaticWeight() > curC.getStaticWeight()) {
            ao = AdjustmentOperation.Increase;
          }
          ai.setOperation(ao);
          ai.setStaticWeight(curC.getStaticWeight() - preC.getStaticWeight());

          return ai;
        })
        .filter(c -> c.getOperation() != AdjustmentOperation.Unchanged)
        .collect(Collectors.toList());

    List<AdjustmentInfo> aisAdded = addedIds
        .stream()
        .map(i -> {
          Constituent c = curConsMap.get(i);
          AdjustmentInfo ai = new AdjustmentInfo();
          ai.setAdjustmentRecord(c.getAdjustmentRecord());
          ai.setAdjustTime(LocalTime.now());
          ai.setSymbol(c.getSymbol());
          ai.setAbbreviation(c.getAbbreviation());
          ai.setOperation(AdjustmentOperation.Join);
          ai.setStaticWeight(c.getStaticWeight());
          return ai;
        })
        .collect(Collectors.toList());

    List<AdjustmentInfo> aisPopped = poppedIds
        .stream()
        .map(i -> {
          Constituent c = preConsMap.get(i);
          AdjustmentInfo ai = new AdjustmentInfo();
          ai.setAdjustmentRecord(c.getAdjustmentRecord());
          ai.setAdjustTime(LocalTime.now());
          ai.setSymbol(c.getSymbol());
          ai.setAbbreviation(c.getAbbreviation());
          ai.setOperation(AdjustmentOperation.Leave);
          ai.setStaticWeight(c.getStaticWeight());
          return ai;
        })
        .collect(Collectors.toList());

    return Stream
        .of(aisRemaining, aisAdded, aisPopped)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

}
