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
  private PortfolioPactRepository repo;

  List<PortfolioPact> getAllPortfolioPacts(Boolean isActive) {
    if (isActive == null) {
      return repo.findAll();
    } else {
      return repo.findByIsActive(isActive);
    }
  }

  public Optional<PortfolioPact> getPortfolioPactById(int id) {
    return repo.findById(id);
  }

  public Optional<PortfolioPact> getPortfolioPactByAlias(String alias) {
    return repo.findByAlias(alias);
  }

  public PortfolioPact createPortfolioPact(PortfolioPact portfolioPact) {
    return repo.save(portfolioPact);
  }

  public Optional<PortfolioPact> updatePortfolioPact(int id, PortfolioPact portfolioPact) {
    return repo.findById(id).map(
        record -> {
          record.setAlias(portfolioPact.getAlias());
          record.setPromoter(portfolioPact.getPromoter());
          record.setIndustryInfo(portfolioPact.getIndustryInfo());
          record.setStartDate(portfolioPact.getStartDate());
          record.setEndDate(portfolioPact.getEndDate());
          record.setDescription(portfolioPact.getDescription());
          record.setIsActive(portfolioPact.getIsActive());
          return repo.save(record);
        });
  }

  public Optional<PortfolioPact> updatePortfolioPact(String alias, PortfolioPact portfolioPact) {
    return repo.findByAlias(alias).map(
        record -> {
          record.setAlias(portfolioPact.getAlias());
          record.setPromoter(portfolioPact.getPromoter());
          record.setIndustryInfo(portfolioPact.getIndustryInfo());
          record.setStartDate(portfolioPact.getStartDate());
          record.setEndDate(portfolioPact.getEndDate());
          record.setDescription(portfolioPact.getDescription());
          record.setIsActive(portfolioPact.getIsActive());
          return repo.save(record);
        });
  }

  public void deletePortfolioPact(int id) {
    repo.deleteById(id);
  }

  public void deletePortfolioPact(String alias) {
    repo.deleteByAlias(alias);
  }

}
