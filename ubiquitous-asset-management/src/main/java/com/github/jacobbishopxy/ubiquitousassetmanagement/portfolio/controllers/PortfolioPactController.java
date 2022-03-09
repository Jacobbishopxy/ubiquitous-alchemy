/**
 * Created by Jacob Xie on 3/8/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.controllers;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioPactInput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioPactOutput;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioPact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.PortfolioPactService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.services.IndustryInfoService;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.services.PromoterService;

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
  private PortfolioPactService ppService;

  @Autowired
  private PromoterService pService;

  @Autowired
  private IndustryInfoService iiService;

  @GetMapping(value = "/portfolio_pact")
  List<PortfolioPactOutput> getPortfolioPacts(
      @RequestParam(value = "isActive", required = false) Boolean isActive) {
    return ppService.getAllPortfolioPacts(isActive)
        .stream()
        .map(pp -> PortfolioPactOutput.fromPortfolioPact(pp))
        .toList();
  }

  @GetMapping(value = "/portfolio_pact/{id}")
  PortfolioPactOutput getPortfolioPact(@PathVariable("id") int id) {
    PortfolioPact pp = ppService.getPortfolioPactById(id).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PortfolioPact id: %s not found", id)));
    return PortfolioPactOutput.fromPortfolioPact(pp);
  }

  @GetMapping(value = "/portfolio_pact_by_alias")
  PortfolioPactOutput getPortfolioPact(@RequestParam("alias") String alias) {
    PortfolioPact pp = ppService.getPortfolioPactByAlias(alias).orElseThrow(
        () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PortfolioPact alias: %s not found", alias)));
    return PortfolioPactOutput.fromPortfolioPact(pp);
  }

  @PostMapping(value = "/portfolio_pact")
  PortfolioPactOutput createPortfolioPact(@RequestBody PortfolioPactInput dto) {
    String email = pService
        .getEmailByNickname(dto.promoter())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Promoter nickname: %s not found", dto.promoter())));

    int indId = iiService
        .getIndustryInfoByName(dto.industry())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Industry name: %s not found", dto.industry())))
        .getId();

    PortfolioPact pp = PortfolioPact.fromPortfolioPactDto(dto, email, indId);

    pp = ppService.createPortfolioPact(pp);

    return PortfolioPactOutput.fromPortfolioPact(pp, dto.promoter(), dto.industry());
  }

  @PutMapping(value = "/portfolio_pact/{id}")
  PortfolioPactOutput updatePortfolioPact(
      @PathVariable("id") int id,
      @RequestBody PortfolioPactInput dto) {
    String email = pService
        .getEmailByNickname(dto.promoter())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Promoter nickname: %s not found", dto.promoter())));

    int indId = iiService
        .getIndustryInfoByName(dto.industry())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Industry name: %s not found", dto.industry())))
        .getId();

    PortfolioPact pp = PortfolioPact.fromPortfolioPactDto(dto, email, indId);

    pp = ppService
        .updatePortfolioPact(id, pp)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("PortfolioPact id: %s not found", id)));

    return PortfolioPactOutput.fromPortfolioPact(pp, dto.promoter(), dto.industry());
  }

  @DeleteMapping(value = "/portfolio_pact/{id}")
  void deletePortfolioPact(@PathVariable("id") int id) {
    ppService.deletePortfolioPact(id);
  }

  @DeleteMapping(value = "/portfolio_pact_by_alias")
  void deletePortfolioPact(@RequestParam("alias") String alias) {
    ppService.deletePortfolioPact(alias);
  }

}
