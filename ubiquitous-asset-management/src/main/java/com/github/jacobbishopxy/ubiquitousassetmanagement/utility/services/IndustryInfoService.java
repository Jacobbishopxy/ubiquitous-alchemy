/**
 * Created by Jacob Xie on 2/27/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.utility.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.IndustryInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.repositories.IndustryInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndustryInfoService {

  @Autowired
  private IndustryInfoRepository repo;

  public List<IndustryInfo> getIndustryInfos() {
    return repo.findAll();
  }

  public Optional<IndustryInfo> getIndustryInfo(Integer id) {
    return repo.findById(id);
  }

  public Optional<IndustryInfo> getIndustryInfoByName(String name) {
    return repo.findByName(name);
  }

  public IndustryInfo createIndustryInfo(IndustryInfo industryInfo) {
    return repo.save(industryInfo);
  }

  public Optional<IndustryInfo> updateIndustryInfo(Integer id, IndustryInfo industryInfo) {
    return repo.findById(id).map(
        record -> {
          record.setName(industryInfo.getName());
          record.setDescription(industryInfo.getDescription());
          return repo.save(record);
        });
  }

  public void deleteIndustryInfo(Integer id) {
    repo.deleteById(id);
  }

}
