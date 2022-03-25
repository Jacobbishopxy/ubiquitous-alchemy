/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.BenchmarkInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.BenchmarkUpdate;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.BenchmarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
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
  @Operation(summary = "Get all benchmarks, either by adjustment record id or ids.")
  List<Benchmark> getBenchmarksByAdjustmentRecordId(
      @RequestParam(value = "adjustment_record_id", required = false) Long adjustmentRecordId,
      @RequestParam(value = "adjustment_record_ids", required = false) List<Long> adjustmentRecordIds) {
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
  @Operation(summary = "Get benchmark by id.")
  Benchmark getBenchmarkById(@PathVariable("id") Long id) {
    return benchmarkService
        .getBenchmarkById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Benchmark for id: %s not found", id)));
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping("/benchmark")
  @Operation(summary = "Create benchmark.")
  Benchmark createBenchmark(@RequestBody BenchmarkInput dto) {
    return benchmarkService.createBenchmark(BenchmarkInput.intoBenchmark(dto));
  }

  @PostMapping("/benchmarks")
  @Operation(summary = "Create multiple benchmarks.")
  List<Benchmark> createBenchmarks(@RequestBody List<BenchmarkInput> dto) {
    List<Benchmark> benchmarks = dto
        .stream()
        .map(BenchmarkInput::intoBenchmark)
        .collect(Collectors.toList());

    return benchmarkService.createBenchmarks(benchmarks);
  }

  @PutMapping("/benchmark/{id}")
  @Operation(summary = "Update benchmark.")
  Benchmark updateBenchmark(
      @PathVariable("id") Long id,
      @RequestBody BenchmarkInput dto) {
    return benchmarkService
        .updateBenchmark(id, BenchmarkInput.intoBenchmark(dto))
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("Benchmark for id: %s not found", id)));
  }

  @PutMapping("/benchmarks")
  @Operation(summary = "Update multiple benchmarks.")
  List<Benchmark> updateBenchmarks(
      @RequestBody List<BenchmarkInput> dto) {
    List<Benchmark> benchmarks = dto
        .stream()
        .map(BenchmarkInput::intoBenchmark)
        .collect(Collectors.toList());

    return benchmarkService.updateBenchmarks(benchmarks);
  }

  @PatchMapping("/benchmark/{id}")
  @Operation(summary = "Update a benchmark's percentage change.")
  Benchmark modifyBenchmark(
      @PathVariable("id") Long id,
      @RequestBody BenchmarkUpdate dto) {
    return benchmarkService
        .modifyBenchmark(id, dto)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("Benchmark for id: %s not found", id)));
  }

  @DeleteMapping("/benchmark/{id}")
  @Operation(summary = "Delete benchmark.")
  void deleteBenchmark(@PathVariable("id") Long id) {
    benchmarkService.deleteBenchmark(id);
  }

  @DeleteMapping("/benchmarks")
  @Operation(summary = "Delete multiple benchmarks.")
  void deleteBenchmarks(@RequestParam("ids") List<Long> ids) {
    benchmarkService.deleteBenchmarks(ids);
  }

}
