/**
 * Created by Jacob Xie on 3/8/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.Constants;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PactInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PactOutput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.PactService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.IndustryInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.services.IndustryInfoService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.services.PromoterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Portfolio")
@RestController
@RequestMapping(Constants.API_VERSION + Constants.API_PORTFOLIO)
public class PactController {

  @Autowired
  private PactService ppService;

  @Autowired
  private PromoterService pService;

  @Autowired
  private IndustryInfoService iiService;

  // =======================================================================
  // Query methods
  // =======================================================================

  @GetMapping(value = "/pact")
  List<PactOutput> getPortfolioPacts(
      @RequestParam(value = "isActive", required = false) Boolean isActive) {
    return ppService.getAllPacts(isActive)
        .stream()
        .map(pp -> PactOutput.fromPortfolioPact(pp))
        .toList();
  }

  @GetMapping(value = "/pact/{id}")
  PactOutput getPortfolioPact(@PathVariable("id") int id) {
    Pact pp = ppService.getPactById(id).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PortfolioPact id: %s not found", id)));
    return PactOutput.fromPortfolioPact(pp);
  }

  @GetMapping(value = "/pact_by_alias")
  PactOutput getPortfolioPact(@RequestParam("alias") String alias) {
    Pact pp = ppService.getPactByAlias(alias).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PortfolioPact alias: %s not found", alias)));
    return PactOutput.fromPortfolioPact(pp);
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping(value = "/pact")
  PactOutput createPortfolioPact(@RequestBody PactInput dto) {
    Promoter promoter = pService
        .getPromoterByNickname(dto.promoter())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Promoter nickname: %s not found", dto.promoter())));

    IndustryInfo indInfo = iiService
        .getIndustryInfoByName(dto.industry())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Industry name: %s not found", dto.industry())));

    Pact pp = Pact.fromPortfolioPactDto(dto, promoter, indInfo);

    pp = ppService.createPact(pp);

    return PactOutput.fromPortfolioPact(pp, dto.promoter(), dto.industry());
  }

  @PutMapping(value = "/pact/{id}")
  PactOutput updatePortfolioPact(
      @PathVariable("id") int id,
      @RequestBody PactInput dto) {
    Promoter promoter = pService
        .getPromoterByNickname(dto.promoter())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Promoter nickname: %s not found", dto.promoter())));

    IndustryInfo indInfo = iiService
        .getIndustryInfoByName(dto.industry())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Industry name: %s not found", dto.industry())));

    Pact pp = Pact.fromPortfolioPactDto(dto, promoter, indInfo);

    pp = ppService
        .updatePact(id, pp)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PortfolioPact id: %s not found", id)));

    return PactOutput.fromPortfolioPact(pp, dto.promoter(), dto.industry());
  }

  @DeleteMapping(value = "/pact/{id}")
  void deletePortfolioPact(@PathVariable("id") int id) {
    ppService.deletePact(id);
  }

  @DeleteMapping(value = "/pact_by_alias")
  void deletePortfolioPact(@RequestParam("alias") String alias) {
    ppService.deletePact(alias);
  }

}
