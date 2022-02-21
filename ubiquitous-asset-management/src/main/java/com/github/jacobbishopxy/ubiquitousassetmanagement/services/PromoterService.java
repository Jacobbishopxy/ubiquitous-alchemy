/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.services;

import java.util.List;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.Promoter;
import com.github.jacobbishopxy.ubiquitousassetmanagement.repositories.PromoterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoterService {

  @Autowired
  private PromoterRepository repo;

  public List<Promoter> getPromoters() {
    return repo.findAll();
  }

  public String getEmailByNickname(String nickname) {
    return repo.findByNickname(nickname).getEmail();
  }

}
