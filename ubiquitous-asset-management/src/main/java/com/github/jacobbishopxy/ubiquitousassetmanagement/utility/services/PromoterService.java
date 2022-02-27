/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.utility.services;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.repositories.PromoterRepository;

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
