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

  public List<Pact> getAllPacts(Boolean isActive) {
    if (isActive == null) {
      return ppRepo.findAll();
    } else {
      return ppRepo.findByIsActive(isActive);
    }
  }

  public Optional<Pact> getPactById(int id) {
    return ppRepo.findById(id);
  }

  public Optional<Pact> getPactByAlias(String alias) {
    return ppRepo.findByAlias(alias);
  }

  public Pact createPact(Pact portfolioPact) {
    // if alias is not set, use promoter_nickname and start_date as alias
    if (portfolioPact.getAlias() == null) {
      String alias = portfolioPact.getIndustryInfo().getName() + "_" +
          portfolioPact.getPromoter().getNickname() + "_" +
          portfolioPact.getStartDate().toString();
      portfolioPact.setAlias(alias);
    }
    return ppRepo.save(portfolioPact);
  }

  public Optional<Pact> updatePact(int id, Pact portfolioPact) {
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

  public Optional<Pact> updatePact(String alias, Pact portfolioPact) {
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

  public void deletePact(int id) {
    ppRepo.deleteById(id);
  }

  public void deletePact(String alias) {
    ppRepo.deleteByAlias(alias);
  }

}
