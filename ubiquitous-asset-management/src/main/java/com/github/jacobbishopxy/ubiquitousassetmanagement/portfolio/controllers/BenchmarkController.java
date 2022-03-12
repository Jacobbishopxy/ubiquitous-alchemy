/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.BenchmarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PortfolioBenchmark", description = "PortfolioBenchmark related operations")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class BenchmarkController {

  @Autowired
  private BenchmarkService benchmarkService;

  @GetMapping("/benchmark")
  List<Benchmark> getBenchmarksByAdjustmentRecordId(
      @RequestParam(value = "adjustmentRecordId", required = true) Integer adjustmentRecordId) {
    return benchmarkService.getBenchmarksByAdjustmentRecordId(adjustmentRecordId);
  }

  @GetMapping("/benchmark/{id}")
  Benchmark getBenchmarkById(@PathVariable("id") Integer id) {
    return benchmarkService.getBenchmarkById(id).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Benchmark for id: %s not found", id)));
  }

  @GetMapping("/benchmarks")
  List<Benchmark> getBenchmarksByAdjustmentRecordIds(
      @RequestParam(value = "adjustmentRecordIds", required = true) List<Integer> adjustmentRecordIds) {
    return benchmarkService.getBenchmarksByAdjustmentRecordIds(adjustmentRecordIds);
  }

  @PostMapping("/benchmark")
  Benchmark createBenchmark(@RequestBody Benchmark benchmark) {
    return benchmarkService.createBenchmark(benchmark);
  }

  @PutMapping("/benchmark/{id}")
  Benchmark updateBenchmark(
      @PathVariable("id") int id,
      @RequestBody Benchmark benchmark) {
    return benchmarkService.updateBenchmark(id, benchmark).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Benchmark for id: %s not found", id)));
  }

  @DeleteMapping("/benchmark/{id}")
  void deleteBenchmark(@PathVariable("id") int id) {
    benchmarkService.deleteBenchmark(id);
  }
}
