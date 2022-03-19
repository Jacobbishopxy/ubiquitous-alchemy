/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.BenchmarkInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.BenchmarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Portfolio")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class BenchmarkController {

  @Autowired
  private BenchmarkService benchmarkService;

  // =======================================================================
  // Query methods
  // =======================================================================

  @GetMapping("/benchmarks")
  List<Benchmark> getBenchmarksByAdjustmentRecordId(
      @RequestParam(value = "adjustment_record_id", required = false) Integer adjustmentRecordId,
      @RequestParam(value = "adjustment_record_ids", required = false) List<Integer> adjustmentRecordIds) {
    if (adjustmentRecordId != null) {
      return benchmarkService.getBenchmarksByAdjustmentRecordId(adjustmentRecordId);
    } else if (adjustmentRecordIds != null) {
      return benchmarkService.getBenchmarksByAdjustmentRecordIds(adjustmentRecordIds);
    } else {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "adjustment_record_id or adjustment_record_ids is required");
    }
  }

  @GetMapping("/benchmark/{id}")
  Benchmark getBenchmarkById(@PathVariable("id") Integer id) {
    return benchmarkService
        .getBenchmarkById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Benchmark for id: %s not found", id)));
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping("/benchmark")
  Benchmark createBenchmark(@RequestBody BenchmarkInput dto) {
    return benchmarkService.createBenchmark(BenchmarkInput.intoBenchmark(dto));
  }

  @PostMapping("/benchmarks")
  List<Benchmark> createBenchmarks(@RequestBody List<BenchmarkInput> dto) {
    List<Benchmark> benchmarks = dto
        .stream()
        .map(BenchmarkInput::intoBenchmark)
        .collect(Collectors.toList());

    return benchmarkService.createBenchmarks(benchmarks);
  }

  @PutMapping("/benchmark/{id}")
  Benchmark updateBenchmark(
      @PathVariable("id") int id,
      @RequestBody Benchmark benchmark) {
    return benchmarkService
        .updateBenchmark(id, benchmark)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("Benchmark for id: %s not found", id)));
  }

  @PutMapping("/benchmarks")
  List<Benchmark> updateBenchmarks(
      @RequestBody List<BenchmarkInput> dto) {
    List<Benchmark> benchmarks = dto
        .stream()
        .map(BenchmarkInput::intoBenchmark)
        .collect(Collectors.toList());

    return benchmarkService.updateBenchmarks(benchmarks);
  }

  @DeleteMapping("/benchmark/{id}")
  void deleteBenchmark(@PathVariable("id") int id) {
    benchmarkService.deleteBenchmark(id);
  }
}
