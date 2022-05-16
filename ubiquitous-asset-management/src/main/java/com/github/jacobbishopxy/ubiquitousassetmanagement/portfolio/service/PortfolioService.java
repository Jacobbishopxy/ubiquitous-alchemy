/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AccumulatedPerformance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.domain.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.PortfolioDetail;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dto.PortfolioOverview;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.AccumulatedPerformanceRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.AdjustmentInfoRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.AdjustmentRecordRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.BenchmarkRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repository.ConstituentRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.service.helper.PortfolioAdjustmentHelper;
import com.google.common.collect.HashBiMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PortfolioService
 *
 * Full commands:
 *
 * 1. Settle an existing portfolio. AdjustmentRecord/Performance is created. The
 * actual operation is to get the latest adjustment record's date and version,
 * and according to its id, copy the related portfolio benchmarks and
 * constituents to a new date. And then, create a new adjustment record.
 *
 * 2. Cancel a portfolio settle. AdjustmentRecord/Performance is deleted. The
 * actual operation is to get the latest adjustment record's date and version,
 * and according to its id, delete the related portfolio benchmarks and
 * constituents. And then delete the adjustment record.
 *
 */
@Service
public class PortfolioService {

	@Autowired
	private PactService pactService;

	@Autowired
	private AdjustmentRecordRepository adjustmentRecordRepository;

	@Autowired
	private AdjustmentRecordService adjustmentRecordService;

	@Autowired
	private AdjustmentInfoRepository adjustmentInfoRepository;

	@Autowired
	private AdjustmentInfoService adjustmentInfoService;

	@Autowired
	private BenchmarkRepository benchmarkRepository;

	@Autowired
	private BenchmarkService benchmarkService;

	@Autowired
	private ConstituentRepository constituentRepository;

	@Autowired
	private ConstituentService constituentService;

	@Autowired
	private PerformanceService performanceService;

	@Autowired
	private AccumulatedPerformanceRepository accumulatedPerformanceRepository;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private RecalculateService recalculateService;

	// =======================================================================
	// Query methods
	// =======================================================================

	/**
	 * Get a list of portfolios' overviews at latest date's latest version.
	 *
	 * @param isActive
	 * @return
	 */
	public List<PortfolioOverview> getPortfolioOverviews(Boolean isActive) {
		// get all activate(true)/inactivate(false)/all(null) portfolios' pacts
		List<Pact> pacts = pactService.getAllPacts(isActive);
		List<Long> pactIds = pacts.stream().map(Pact::getId).collect(Collectors.toList());

		// IMPORTANT: get all unsettled adjustment records, we assume that each pact has
		// only one unsettled adjustment record
		List<AdjustmentRecord> ars = adjustmentRecordService.getUnsettledARs(pactIds);
		List<Long> arIds = ars.stream().map(AdjustmentRecord::getId).collect(Collectors.toList());

		// snapshot performances, sorted by arIds
		List<Performance> pfm = performanceService.getPerformancesByAdjustmentRecordIds(arIds);
		// accumulated performances, sorted by pactIds
		List<AccumulatedPerformance> aPfm = accumulatedPerformanceRepository.findByPactIdIn(pactIds);
		Map<Long, AccumulatedPerformance> aPfmMap = HashBiMap.create(
				aPfm
						.stream()
						.collect(Collectors.toMap(AccumulatedPerformance::getThePactId, ap -> ap)));

		return pfm
				.stream()
				.map(p -> {
					Pact pact = p.getAdjustmentRecord().getPact();
					AccumulatedPerformance optAp = aPfmMap.get(pact.getId());
					AccumulatedPerformance ap = optAp == null ? new AccumulatedPerformance() : optAp;

					return PortfolioOverview.fromPactAndPerformance(pact, p, ap);
				})
				.collect(Collectors.toList());
	}

	/**
	 * Get a sorted list of adjustment records by pact id, including unsettled and
	 * settled. And unsettled record is always on the top.
	 *
	 * @param pactId
	 * @param pageable
	 * @return
	 */
	public List<AdjustmentRecord> getAdjustmentRecordsByPactId(Long pactId, Pageable pageable, Boolean isAdjusted) {
		if (isAdjusted != null) {
			if (isAdjusted == true) {
				return adjustmentRecordService.getARSortDescAndIsAdjustedTrue(pactId, pageable);
			}
		}

		return adjustmentRecordService.getARSortDesc(pactId, pageable);
	}

	/**
	 * Get an overview of a portfolio by given adjustmentRecord id (id list can be
	 * found in Pact entity).
	 *
	 * @param adjustmentRecordId: nullable. if null means latest adjustment record
	 * @return
	 */
	public Optional<PortfolioOverview> getPortfolioOverview(Long adjustmentRecordId) {
		return adjustmentRecordService
				.getARById(adjustmentRecordId)
				.map(ar -> {
					Pact pact = ar.getPact();
					Performance p = performanceService
							.getPerformanceByAdjustmentRecordId(ar.getId())
							.orElse(new Performance());

					AccumulatedPerformance ap = accumulatedPerformanceRepository
							.findByPactId(pact.getId())
							.orElse(new AccumulatedPerformance());

					return PortfolioOverview.fromPactAndPerformance(pact, p, ap);
				});
	}

	/**
	 * Get an unsettled portfolio detail.
	 *
	 * @param pactId
	 * @return
	 */
	public PortfolioDetail getUnsettledPortfolioDetail(Long pactId) {
		AdjustmentRecord ar = adjustmentRecordService
				.getUnsettledAR(pactId)
				.orElseThrow(() -> new RuntimeException(
						"No unsettled adjustment record found for pact id: " + pactId));
		Long arId = ar.getId();

		List<Benchmark> benchmarks = benchmarkService
				.getBenchmarksByAdjustmentRecordId(arId);
		List<Constituent> constituents = constituentService
				.getConstituentsByAdjustmentRecordId(arId);
		// if performance is null, return empty performance
		Performance performance = performanceService
				.getPerformanceByAdjustmentRecordId(arId)
				.orElse(new Performance());
		List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService
				.getAdjustmentInfosByAdjustmentRecordId(arId);
		AccumulatedPerformance accumulatedPerformance = accumulatedPerformanceRepository
				.findByPactId(pactId)
				.orElse(new AccumulatedPerformance());

		return new PortfolioDetail(ar, constituents, benchmarks, performance, adjustmentInfos, accumulatedPerformance);
	}

	/**
	 * Get a portfolio detail at latest date's latest version.
	 *
	 * @param pactId
	 * @return
	 */
	public PortfolioDetail getLatestSettledPortfolioDetail(Long pactId) {
		AdjustmentRecord ar = adjustmentRecordService
				.getLatestSettledAR(pactId)
				.orElseThrow(() -> new RuntimeException(
						"No latest adjustment record found for pactId: " + pactId));
		Long arId = ar.getId();

		List<Benchmark> benchmarks = benchmarkService
				.getBenchmarksByAdjustmentRecordId(arId);
		List<Constituent> constituents = constituentService
				.getConstituentsByAdjustmentRecordId(arId);
		// if performance is null, return empty performance
		Performance performance = performanceService
				.getPerformanceByAdjustmentRecordId(arId).orElse(new Performance());
		List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService
				.getAdjustmentInfosByAdjustmentRecordId(arId);
		AccumulatedPerformance accumulatedPerformance = accumulatedPerformanceRepository
				.findByPactId(pactId)
				.orElse(new AccumulatedPerformance());

		return new PortfolioDetail(ar, constituents, benchmarks, performance, adjustmentInfos, accumulatedPerformance);
	}

	/**
	 * Get a portfolio detail by adjustment record id. Used for searching history.
	 *
	 * @param adjustmentRecordId
	 * @return
	 */
	public PortfolioDetail getPortfolioDetailByARId(Long adjustmentRecordId) {
		AdjustmentRecord ar = adjustmentRecordService
				.getARById(adjustmentRecordId)
				.orElseThrow(() -> new RuntimeException(
						"No adjustment record found for id: " + adjustmentRecordId));
		Long arId = ar.getId();

		List<Benchmark> benchmarks = benchmarkService
				.getBenchmarksByAdjustmentRecordId(arId);
		List<Constituent> constituents = constituentService
				.getConstituentsByAdjustmentRecordId(arId);
		// if performance is null, return empty performance
		Performance performance = performanceService.getPerformanceByAdjustmentRecordId(arId)
				.orElse(new Performance());
		List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService
				.getAdjustmentInfosByAdjustmentRecordId(arId);
		AccumulatedPerformance accumulatedPerformance = accumulatedPerformanceRepository
				.findByPactId(ar.getPact().getId())
				.orElse(new AccumulatedPerformance());

		return new PortfolioDetail(ar, constituents, benchmarks, performance, adjustmentInfos, accumulatedPerformance);
	}

	// =======================================================================
	// Mutation methods
	// =======================================================================

	/**
	 * Settle a portfolio.
	 *
	 * A powerful method which automatically distinguishes between normal (members
	 * unchanged) settlement and adjustment.
	 *
	 * @param pactId
	 * @param settleDate
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public PortfolioDetail settle(Long pactId, LocalDate settleDate) {
		// get pact, validation check
		Pact pact = pactService
				.getPactById(pactId)
				.orElseThrow(() -> new RuntimeException("No pact found for id: " + pactId));

		// get unsettled adjustment record
		AdjustmentRecord unsettledAr = adjustmentRecordService
				.getUnsettledAR(pactId)
				.orElseThrow(() -> new RuntimeException("No unsettled adjustment record found for pactId: " + pactId));
		Long unsettledArId = unsettledAr.getId();

		// if latest adjustment exists, calculate adjustmentInfos
		List<Constituent> latestSettledCons = adjustmentRecordService
				.getLatestSettledAR(pactId)
				.map(latestSettledAr -> {
					LocalDate latestSettledDate = latestSettledAr.getAdjustDate();
					// settle date should be later than the latest settled date
					if (settleDate.isBefore(latestSettledDate)) {
						throw new RuntimeException(
								String.format(
										"Settle date: %s is before latest settled adjustment record's date: %s",
										settleDate,
										latestSettledDate));
					}

					// modify unsettled record
					if (latestSettledDate.equals(settleDate)) {
						unsettledAr.setAdjustVersion(latestSettledAr.getAdjustVersion() + 1);
					} else {
						unsettledAr.setAdjustVersion(1);
					}

					return constituentService
							.getConstituentsByAdjustmentRecordId(latestSettledAr.getId());
				})
				.orElseGet(() -> {
					// this will only happen once when setting up a new pact
					unsettledAr.setAdjustVersion(1);
					return List.of();
				});

		// get all unsettled constituents, which also represents the current portfolio
		List<Constituent> unsettledCons = constituentService
				.getConstituentsByAdjustmentRecordId(unsettledArId);

		// get adjustment info
		List<AdjustmentInfo> ais = PortfolioAdjustmentHelper.adjust(latestSettledCons, unsettledCons);

		// is adjustment date
		final boolean isAdjusted = !ais.isEmpty();

		// if adjustment infos is not empty, save them
		if (isAdjusted) {
			adjustmentInfoRepository.saveAll(ais);
		}

		// turn unsettled AR to settled AR and save it
		unsettledAr.setAdjustDate(settleDate);
		unsettledAr.setIsUnsettled(null);
		unsettledAr.setIsAdjusted(isAdjusted);
		adjustmentRecordRepository.saveAndFlush(unsettledAr);

		// create a new adjustment record
		AdjustmentRecord tmpAr = new AdjustmentRecord();
		tmpAr.setPact(pact);
		tmpAr.setIsUnsettled(true);
		tmpAr.setIsAdjusted(isAdjusted);
		final AdjustmentRecord newAr = adjustmentRecordRepository.save(tmpAr);

		// copy constituents, bind to new adjustment record and save
		List<Constituent> newCons = unsettledCons
				.stream()
				.map(c -> {
					Constituent newC = new Constituent(c);
					newC.setId(null);
					newC.setAdjustmentRecord(newAr);
					if (isAdjusted) {
						newC.setAdjustDate(settleDate);
					}
					return newC;
				})
				.collect(Collectors.toList());
		// validate unsettled constituents
		validationService.checkConstituentsTotalWeightIsWithinRange(newCons);
		newCons = constituentRepository.saveAll(newCons);

		// copy benchmarks, bind to new adjustment record and save
		List<Benchmark> newBms = benchmarkService
				.getBenchmarksByAdjustmentRecordId(unsettledArId)
				.stream()
				.map(b -> {
					Benchmark newB = new Benchmark(b);
					newB.setId(null);
					newB.setAdjustmentRecord(newAr);
					if (isAdjusted) {
						newB.setAdjustDate(settleDate);
					}
					return newB;
				})
				.collect(Collectors.toList());
		// validate unsettled benchmarks
		validationService.checkBenchmarksTotalWeightIsWithinRange(newBms);
		newBms = benchmarkRepository.saveAll(newBms);

		// copy performance, bind to new adjustment record and save
		Performance unsettledPfm = performanceService
				.getPerformanceByAdjustmentRecordId(unsettledArId).get();
		Performance newPfm = new Performance(unsettledPfm);
		newPfm.setId(null);
		newPfm.setAdjustmentRecord(newAr);
		newPfm = performanceService.createPerformance(newPfm);

		// recalculate accumulated performance and save it
		AccumulatedPerformance ap = recalculateService.recalculateAccumulatedPerformance(pact, true, isAdjusted);
		ap = accumulatedPerformanceRepository.save(ap);

		return new PortfolioDetail(newAr, newCons, newBms, newPfm, ais, ap);
	}

	@Transactional(rollbackFor = Exception.class)
	public void cancelSettle(Long pactId) {
		AdjustmentRecord ar = adjustmentRecordService
				.getLatestSettledAR(pactId)
				.orElseThrow(() -> new RuntimeException(
						"No latest adjustment record found for pactId: " + pactId));

		// delete benchmarks
		benchmarkService.deleteBenchmarksByAdjustmentRecordId(ar.getId());

		// delete constituents
		constituentService.deleteConstituentsByAdjustmentRecordId(ar.getId());

		// delete performance
		performanceService.deletePerformanceByAdjustmentRecordId(ar.getId());

		// recalculate accumulated performance and save it
		boolean isAdjusted = ar.getIsAdjusted() == true ? true : false;
		Pact pact = new Pact(pactId);
		AccumulatedPerformance ap = recalculateService.recalculateAccumulatedPerformance(pact, false, isAdjusted);
		accumulatedPerformanceRepository.save(ap);

		// delete adjustment info
		adjustmentInfoService.deleteAdjustmentInfosByAdjustmentRecordId(ar.getId());

		// delete the latest adjustment record
		adjustmentRecordService.deleteAR(ar.getId());
	}

}
