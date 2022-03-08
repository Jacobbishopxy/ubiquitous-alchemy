/**
 * Created by Jacob Xie on 3/8/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioPact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.PortfolioPactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PortfolioPact", description = "PortfolioPact related operations")
@RestController
@RequestMapping("v1")
public class PortfolioPactController {

  @Autowired
  private PortfolioPactService service;

  @GetMapping(value = "/portfolio_pact")
  List<PortfolioPact> getPortfolioPacts(
      @RequestParam(value = "isActive", required = false) Boolean isActive) {
    return service.getAllPortfolioPacts(isActive);
  }

  @GetMapping(value = "/portfolio_pact/{id}")
  PortfolioPact getPortfolioPact(@PathVariable("id") int id) {
    return service.getPortfolioPactById(id).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PortfolioPact id: %s not found", id)));
  }

  @GetMapping(value = "/portfolio_pact_by_alias")
  PortfolioPact getPortfolioPact(@RequestParam("alias") String alias) {
    return service.getPortfolioPactByAlias(alias).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PortfolioPact alias: %s not found", alias)));
  }

  @PostMapping(value = "/portfolio_pact")
  PortfolioPact createPortfolioPact(@RequestBody PortfolioPact portfolioPact) {
    // portfolioPact.validate();
    return service.createPortfolioPact(portfolioPact);
  }

  @PutMapping(value = "/portfolio_pact/{id}")
  PortfolioPact updatePortfolioPact(
      @PathVariable("id") int id, @RequestBody PortfolioPact portfolioPact) {
    // portfolioPact.validate();
    return service
        .updatePortfolioPact(id, portfolioPact)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("PortfolioPact id: %s not found", id)));
  }

  @DeleteMapping(value = "/portfolio_pact/{id}")
  void deletePortfolioPact(@PathVariable("id") int id) {
    service.deletePortfolioPact(id);
  }

  @DeleteMapping(value = "/portfolio_pact_by_alias")
  void deletePortfolioPact(@RequestParam("alias") String alias) {
    service.deletePortfolioPact(alias);
  }

}
