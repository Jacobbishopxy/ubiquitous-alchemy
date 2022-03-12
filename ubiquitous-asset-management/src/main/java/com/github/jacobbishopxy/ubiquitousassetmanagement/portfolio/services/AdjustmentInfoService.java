/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.AdjustmentInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AdjustmentInfoService
 */
@Service
public class AdjustmentInfoService {

  @Autowired
  private AdjustmentInfoRepository adjInfoRepo;

}
