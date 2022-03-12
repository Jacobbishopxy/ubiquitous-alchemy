/**
 * Created by Jacob Xie on 3/11/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.ConstituentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstituentService {

  @Autowired
  private ConstituentRepository pcRepo;

  public List<Constituent> getConstituentsByAdjustmentRecordId(int adjustmentRecordId) {
    return pcRepo.findByAdjustmentRecordId(adjustmentRecordId);
  }

  public List<Constituent> getConstituentsByAdjustmentRecordIds(List<Integer> adjustmentRecordIds) {
    return pcRepo.findByAdjustmentRecordIdIn(adjustmentRecordIds);
  }

  public Optional<Constituent> getConstituentById(int id) {
    return pcRepo.findById(id);
  }

  public Constituent createConstituent(Constituent portfolioConstituent) {
    return pcRepo.save(portfolioConstituent);
  }

  public Optional<Constituent> updateConstituent(int id, Constituent portfolioConstituent) {
    return pcRepo.findById(id).map(
        record -> {
          record.setAdjustDate(portfolioConstituent.getAdjustDate());
          record.setSymbol(portfolioConstituent.getSymbol());
          record.setAbbreviation(portfolioConstituent.getAbbreviation());
          record.setAdjustDatePrice(portfolioConstituent.getAdjustDatePrice());
          record.setCurrentPrice(portfolioConstituent.getCurrentPrice());
          record.setAdjustDateFactor(portfolioConstituent.getAdjustDateFactor());
          record.setCurrentFactor(portfolioConstituent.getCurrentFactor());
          record.setAdjustDateWeight(portfolioConstituent.getAdjustDateWeight());
          record.setCurrentWeight(portfolioConstituent.getCurrentWeight());
          record.setPbpe(portfolioConstituent.getPbpe());
          record.setMarketValue(portfolioConstituent.getMarketValue());
          record.setEarningsYield(portfolioConstituent.getEarningsYield());
          return pcRepo.save(record);
        });
  }

  public void deleteConstituent(int id) {
    pcRepo.deleteById(id);
  }

}
