/**
 * Created by Jacob Xie on 3/7/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.PactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PactService {

  @Autowired
  private PactRepository ppRepo;

  public List<Pact> getAllPortfolioPacts(Boolean isActive) {
    if (isActive == null) {
      return ppRepo.findAll();
    } else {
      return ppRepo.findByIsActive(isActive);
    }
  }

  public Optional<Pact> getPortfolioPactById(int id) {
    return ppRepo.findById(id);
  }

  public Optional<Pact> getPortfolioPactByAlias(String alias) {
    return ppRepo.findByAlias(alias);
  }

  public Pact createPortfolioPact(Pact portfolioPact) {
    // if alias is not set, use promoter_nickname and start_date as alias
    if (portfolioPact.getAlias() == null) {
      String alias = portfolioPact.getIndustryInfo().getName() + "_" +
          portfolioPact.getPromoter().getNickname() + "_" +
          portfolioPact.getStartDate().toString();
      portfolioPact.setAlias(alias);
    }
    return ppRepo.save(portfolioPact);
  }

  public Optional<Pact> updatePortfolioPact(int id, Pact portfolioPact) {
    return ppRepo.findById(id).map(
        record -> {
          record.setAlias(portfolioPact.getAlias());
          record.setPromoter(portfolioPact.getPromoter());
          record.setIndustryInfo(portfolioPact.getIndustryInfo());
          record.setStartDate(portfolioPact.getStartDate());
          record.setEndDate(portfolioPact.getEndDate());
          record.setDescription(portfolioPact.getDescription());
          record.setIsActive(portfolioPact.getIsActive());
          return ppRepo.save(record);
        });
  }

  public Optional<Pact> updatePortfolioPact(String alias, Pact portfolioPact) {
    return ppRepo.findByAlias(alias).map(
        record -> {
          record.setAlias(portfolioPact.getAlias());
          record.setPromoter(portfolioPact.getPromoter());
          record.setIndustryInfo(portfolioPact.getIndustryInfo());
          record.setStartDate(portfolioPact.getStartDate());
          record.setEndDate(portfolioPact.getEndDate());
          record.setDescription(portfolioPact.getDescription());
          record.setIsActive(portfolioPact.getIsActive());
          return ppRepo.save(record);
        });
  }

  public void deletePortfolioPact(int id) {
    ppRepo.deleteById(id);
  }

  public void deletePortfolioPact(String alias) {
    ppRepo.deleteByAlias(alias);
  }

}
