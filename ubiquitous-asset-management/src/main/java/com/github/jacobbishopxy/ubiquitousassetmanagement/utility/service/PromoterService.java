/**
 * Created by Jacob Xie on 2/21/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.utility.service;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.domain.Promoter;
import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.repository.PromoterRepository;

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

  public Optional<Promoter> getPromoterByNickname(String nickname) {
    return repo.findByNickname(nickname);
  }

  public Optional<String> getEmailByNickname(String nickname) {
    return repo.findEmailByNickname(nickname);
  }

  public List<String> getEmailByNicknames(List<String> nicknames) {
    return repo.findEmailsByNicknameIn(nicknames);
  }

}
