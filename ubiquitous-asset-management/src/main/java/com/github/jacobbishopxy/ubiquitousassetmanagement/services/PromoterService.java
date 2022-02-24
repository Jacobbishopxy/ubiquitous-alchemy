/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.models.Promoter;
import com.github.jacobbishopxy.ubiquitousassetmanagement.repositories.PromoterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PromoterService
 * 
 * PromoterService is used to manage promoters.
 */
@Service
public class PromoterService {

  @Autowired
  private PromoterRepository repo;

  public List<Promoter> getPromoters() {
    return repo.findAll();
  }

  public Optional<String> getEmailByNickname(String nickname) {
    return repo
        .findByNickname(nickname)
        .map(Promoter::getEmail);
  }

}
