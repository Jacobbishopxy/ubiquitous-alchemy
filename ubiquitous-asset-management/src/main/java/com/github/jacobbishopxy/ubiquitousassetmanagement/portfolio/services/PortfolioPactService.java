/**
 * Created by Jacob Xie on 3/7/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.PortfolioPact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.PortfolioPactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioPactService {

  @Autowired
  private PortfolioPactRepository ppRepo;

  public List<PortfolioPact> getAllPortfolioPacts(Boolean isActive) {
    if (isActive == null) {
      return ppRepo.findAll();
    } else {
      return ppRepo.findByIsActive(isActive);
    }
  }

  public Optional<PortfolioPact> getPortfolioPactById(int id) {
    return ppRepo.findById(id);
  }

  public Optional<PortfolioPact> getPortfolioPactByAlias(String alias) {
    return ppRepo.findByAlias(alias);
  }

  public PortfolioPact createPortfolioPact(PortfolioPact portfolioPact) {
    // if alias is not set, use promoter_nickname and start_date as alias
    if (portfolioPact.getAlias() == null) {
      String alias = portfolioPact.getIndustryInfo().getName() + "-" +
          portfolioPact.getPromoter().getNickname() + "-" +
          portfolioPact.getStartDate().toString();
      portfolioPact.setAlias(alias);
    }
    return ppRepo.save(portfolioPact);
  }

  public Optional<PortfolioPact> updatePortfolioPact(int id, PortfolioPact portfolioPact) {
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

  public Optional<PortfolioPact> updatePortfolioPact(String alias, PortfolioPact portfolioPact) {
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
