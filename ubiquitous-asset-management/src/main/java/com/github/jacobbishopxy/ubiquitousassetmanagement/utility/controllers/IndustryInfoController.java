/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.utility.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.IndustryInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.services.IndustryInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Utility")
@RestController
@RequestMapping("v1")
public class IndustryInfoController {

  @Autowired
  private IndustryInfoService service;

  @GetMapping("/industry_infos")
  @Operation(summary = "Get all industry info.")
  List<IndustryInfo> getIndustryInfos() {
    return service.getIndustryInfos();
  }

  @GetMapping("/industry_info/{id}")
  @Operation(summary = "Get an industry info by id.")
  IndustryInfo getIndustryInfo(@PathVariable("id") Integer id) {
    return service
        .getIndustryInfo(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("IndustryInfo %s not found", id)));
  }

  @GetMapping("/industry_info")
  @Operation(summary = "Get an industry info by name.")
  IndustryInfo getIndustryInfoByName(@RequestParam(value = "name", required = true) String name) {
    return service
        .getIndustryInfoByName(name)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("IndustryInfo %s not found", name)));
  }

  @PostMapping("/industry_info")
  @Operation(summary = "Create an industry info.")
  IndustryInfo createIndustryInfo(@RequestBody IndustryInfo industryInfo) {
    return service.createIndustryInfo(industryInfo);
  }

  @PutMapping("/industry_info/{id}")
  @Operation(summary = "Update an industry info.")
  IndustryInfo updateIndustryInfo(
      @PathVariable("id") Integer id,
      @RequestBody IndustryInfo industryInfo) {
    return service
        .updateIndustryInfo(id, industryInfo)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("IndustryInfo %s not found", id)));
  }

  @DeleteMapping("/industry_info/{id}")
  @Operation(summary = "Delete an industry info.")
  void deleteIndustryInfo(@PathVariable("id") Integer id) {
    service.deleteIndustryInfo(id);
  }
}
