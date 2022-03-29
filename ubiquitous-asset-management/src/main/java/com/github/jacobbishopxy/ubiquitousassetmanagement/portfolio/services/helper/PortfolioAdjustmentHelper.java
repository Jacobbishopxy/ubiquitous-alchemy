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
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
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

    if (preCons.size() < 1 || curCons.size() < 1) {
      throw new IllegalArgumentException("Constituents must be non-empty");
    }

    final AdjustmentRecord curAr = curCons.get(0).getAdjustmentRecord();

    Map<String, Constituent> preConsMap = HashBiMap
        .create(preCons
            .stream()
            .collect(Collectors.toMap(Constituent::getSymbol, c -> c)));
    Map<String, Constituent> curConsMap = HashBiMap
        .create(curCons
            .stream()
            .collect(Collectors.toMap(Constituent::getSymbol, c -> c)));

    Set<String> preConsSymbols = Sets.newHashSet(preConsMap.keySet());
    Set<String> curConsSymbols = Sets.newHashSet(curConsMap.keySet());

    // remaining: if the current static weight is less than the previous one, mark
    // the constituent as `Decrease`; otherwise, mark it as `Increase`.
    Set<String> remainingSymbols = intersection(preConsSymbols, curConsSymbols);
    // added: mark it as `Join`.
    Set<String> addedSymbols = difference(curConsSymbols, preConsSymbols);
    // popped: mark it as `Leave`.
    Set<String> poppedSymbols = difference(preConsSymbols, curConsSymbols);

    List<AdjustmentInfo> aisRemaining = remainingSymbols
        .stream()
        .map(i -> {
          Constituent preC = preConsMap.get(i);
          Constituent curC = curConsMap.get(i);

          AdjustmentInfo ai = new AdjustmentInfo();
          // NOTICE: adjustment should use the current adjustment record
          ai.setAdjustmentRecord(curAr);
          ai.setAdjustTime(LocalTime.now());
          ai.setSymbol(preC.getSymbol());
          ai.setAbbreviation(preC.getAbbreviation());
          // operation based on the static weight's change
          AdjustmentOperation ao = AdjustmentOperation.Unchanged;
          if (preC.getStaticWeight() < curC.getStaticWeight()) {
            ao = AdjustmentOperation.Increase;
          } else if (preC.getStaticWeight() > curC.getStaticWeight()) {
            ao = AdjustmentOperation.Decrease;
          }
          ai.setOperation(ao);
          ai.setPreviousStaticWeight(preC.getStaticWeight());
          ai.setCurrentStaticWeight(curC.getStaticWeight());
          ai.setStaticWeight(curC.getStaticWeight() - preC.getStaticWeight());

          return ai;
        })
        .filter(c -> c.getOperation() != AdjustmentOperation.Unchanged)
        .collect(Collectors.toList());

    List<AdjustmentInfo> aisAdded = addedSymbols
        .stream()
        .map(i -> {
          Constituent c = curConsMap.get(i);
          AdjustmentInfo ai = new AdjustmentInfo();
          // NOTICE: adjustment should use the current adjustment record
          ai.setAdjustmentRecord(curAr);
          ai.setAdjustTime(LocalTime.now());
          ai.setSymbol(c.getSymbol());
          ai.setAbbreviation(c.getAbbreviation());
          ai.setOperation(AdjustmentOperation.Join);
          ai.setPreviousStaticWeight(0f);
          ai.setCurrentStaticWeight(c.getStaticWeight());
          ai.setStaticWeight(c.getStaticWeight());
          return ai;
        })
        .collect(Collectors.toList());

    List<AdjustmentInfo> aisPopped = poppedSymbols
        .stream()
        .map(i -> {
          Constituent c = preConsMap.get(i);
          AdjustmentInfo ai = new AdjustmentInfo();
          // NOTICE: adjustment should use the current adjustment record
          ai.setAdjustmentRecord(curAr);
          ai.setAdjustTime(LocalTime.now());
          ai.setSymbol(c.getSymbol());
          ai.setAbbreviation(c.getAbbreviation());
          ai.setOperation(AdjustmentOperation.Leave);
          ai.setPreviousStaticWeight(c.getStaticWeight());
          ai.setCurrentStaticWeight(0f);
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
