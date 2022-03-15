/**
 * Created by Jacob Xie on 3/15/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuavaTests {

  @Test
  void operationOfSets1() {
    List<String> foo = List.of("a", "b", "c", "c");

    // Set<String> fooSet = ImmutableSet.copyOf(foo);
    Set<String> fooSet = Sets.newHashSet(foo);

    Set<String> barSet = Sets.newHashSet("a", "d");

    fooSet.retainAll(barSet);

    assertEquals(fooSet.size(), 1);
    assertEquals(barSet.size(), 2);
  }

  @Test
  void operationOfSets2() {
    List<String> foo = List.of();

    // Set<String> fooSet = ImmutableSet.copyOf(foo);
    Set<String> fooSet = Sets.newHashSet(foo);

    Set<String> barSet = Sets.newHashSet("a", "d");

    fooSet.retainAll(barSet);

    assertTrue(fooSet.isEmpty());
  }

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

  @Test
  void intersectionAndDifference() {

    Set<String> foo = Sets.newHashSet("a", "b", "c");

    Set<String> bar = Sets.newHashSet("a", "d");

    Set<String> intersection = intersection(foo, bar);
    // ("a")
    assertEquals(intersection.size(), 1);

    Set<String> diff1 = difference(foo, bar);
    // ("b", "c")
    assertEquals(diff1.size(), 2);

    // ("d")
    Set<String> diff2 = difference(bar, foo);
    assertEquals(diff2.size(), 1);
  }

  @Test
  void testHashBiMap() {
    List<String> foo = List.of("a", "b", "c");

    Map<String, String> fooMap = HashBiMap.create(
        foo.stream().collect(
            Collectors.toMap(
                s -> s,
                s -> s + "-" + "foo")));

    assertEquals(fooMap.get("a"), "a-foo");
    assertEquals(fooMap.get("b"), "b-foo");
    assertEquals(fooMap.get("c"), "c-foo");

    assertEquals(fooMap.keySet(), Sets.newHashSet(foo));
  }
}
