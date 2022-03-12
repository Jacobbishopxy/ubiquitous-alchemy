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

  @Operation(description = "Get all industry info.")
  @GetMapping("/industry_info")
  List<IndustryInfo> getIndustryInfos() {
    return service.getIndustryInfos();
  }

  @Operation(description = "Get an industry info by id.")
  @GetMapping("/industry_info/{id}")
  IndustryInfo getIndustryInfo(@PathVariable("id") Integer id) {
    return service.getIndustryInfo(id).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("IndustryInfo %s not found", id)));
  }

  @Operation(description = "Get an industry info by name.")
  @GetMapping("/industry_info_by_name")
  IndustryInfo getIndustryInfoByName(@RequestParam String name) {
    return service.getIndustryInfoByName(name).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("IndustryInfo %s not found", name)));
  }

  @Operation(description = "Create an industry info.")
  @PostMapping("/industry_info")
  IndustryInfo createIndustryInfo(@RequestBody IndustryInfo industryInfo) {
    return service.createIndustryInfo(industryInfo);
  }

  @Operation(description = "Update an industry info.")
  @PutMapping("/industry_info/{id}")
  IndustryInfo updateIndustryInfo(
      @PathVariable("id") Integer id,
      @RequestBody IndustryInfo industryInfo) {
    return service
        .updateIndustryInfo(id, industryInfo)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("IndustryInfo %s not found", id)));
  }

  @Operation(description = "Delete an industry info.")
  @DeleteMapping("/industry_info/{id}")
  void deleteIndustryInfo(@PathVariable("id") Integer id) {
    service.deleteIndustryInfo(id);
  }
}
