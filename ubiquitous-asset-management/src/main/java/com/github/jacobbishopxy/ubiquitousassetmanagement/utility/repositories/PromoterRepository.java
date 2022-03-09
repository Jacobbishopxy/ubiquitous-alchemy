/**
 * Created by Jacob Xie on 2/17/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.utility.repositories;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.utility.models.Promoter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromoterRepository extends JpaRepository<Promoter, String> {

  @Query("select p.email from Promoter p where p.nickname = ?1")
  public Optional<String> findEmailByNickname(String nickname);

  @Query("select p.email from Promoter p where p.nickname in (:nicknames)")
  public List<String> findEmailsByNicknameIn(List<String> nicknames);

  public Optional<Promoter> findByNickname(String nickname);

}
