/**
 * Created by Jacob Xie on 3/12/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioOverview;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.dtos.PortfolioDetail;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentInfo;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Benchmark;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Constituent;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Pact;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.Performance;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.AdjustmentRecordRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.BenchmarkRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories.ConstituentRepository;
import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.services.helper.PortfolioAdjustmentHelper;

import org.springframework.beans.factory.annotation.Autowired;
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

		List<Performance> pfm = performanceService.getPerformancesByAdjustmentRecordIds(arIds);

		return pfm
				.stream()
				.map(p -> {
					Pact pact = p.getAdjustmentRecord().getPact();
					return PortfolioOverview.fromPactAndPerformance(pact, p);
				})
				.collect(Collectors.toList());
	}

	/**
	 * Get a sorted list of adjustment records by pact id, including unsettled and
	 * settled. And unsettled record is always on the top.
	 *
	 * @param pactId
	 * @return
	 */
	public List<AdjustmentRecord> getAdjustmentRecordsByPactId(Long pactId) {
		return adjustmentRecordService.getARSortDesc(pactId);
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
				.flatMap(ar -> {
					return performanceService
							.getPerformanceByAdjustmentRecordId(ar.getId())
							.map(p -> {
								Pact pact = ar.getPact();
								return PortfolioOverview.fromPactAndPerformance(pact, p);
							});
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

		List<Benchmark> benchmarks = benchmarkService.getBenchmarksByAdjustmentRecordId(arId);
		List<Constituent> constituents = constituentService.getConstituentsByAdjustmentRecordId(arId);
		// if performance is null, return empty performance
		Performance performance = performanceService.getPerformanceByAdjustmentRecordId(arId).orElse(new Performance());
		List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService.getAdjustmentInfosByAdjustmentRecordId(arId);

		return new PortfolioDetail(ar, constituents, benchmarks, performance, adjustmentInfos);
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

		List<Benchmark> benchmarks = benchmarkService.getBenchmarksByAdjustmentRecordId(arId);
		List<Constituent> constituents = constituentService.getConstituentsByAdjustmentRecordId(arId);
		// if performance is null, return empty performance
		Performance performance = performanceService.getPerformanceByAdjustmentRecordId(arId).orElse(new Performance());
		List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService.getAdjustmentInfosByAdjustmentRecordId(arId);

		return new PortfolioDetail(ar, constituents, benchmarks, performance, adjustmentInfos);
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

		List<Benchmark> benchmarks = benchmarkService.getBenchmarksByAdjustmentRecordId(arId);
		List<Constituent> constituents = constituentService.getConstituentsByAdjustmentRecordId(arId);
		// if performance is null, return empty performance
		Performance performance = performanceService.getPerformanceByAdjustmentRecordId(arId).orElse(new Performance());
		List<AdjustmentInfo> adjustmentInfos = adjustmentInfoService.getAdjustmentInfosByAdjustmentRecordId(arId);

		return new PortfolioDetail(ar, constituents, benchmarks, performance, adjustmentInfos);
	}

	// =======================================================================
	// Mutation methods
	// =======================================================================

	/**
	 * Settle a portfolio.
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

		// get all unsettled data
		List<Constituent> unsettledCons = constituentService
				.getConstituentsByAdjustmentRecordId(unsettledArId);

		// if latest adjustment exists, calculate adjustmentInfos
		List<AdjustmentInfo> ais = adjustmentRecordService
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

					List<Constituent> latestSettledCons = constituentService
							.getConstituentsByAdjustmentRecordId(latestSettledAr.getId());

					return PortfolioAdjustmentHelper.adjust(latestSettledCons, unsettledCons);
				})
				.orElseGet(() -> {
					// this will only happen once when setting up a new pact
					unsettledAr.setAdjustVersion(1);
					return List.of();
				});

		// turn unsettled AR to settled AR and save it
		unsettledAr.setAdjustDate(settleDate);
		unsettledAr.setIsUnsettled(null);
		adjustmentRecordRepository.saveAndFlush(unsettledAr);

		// create a new adjustment record
		AdjustmentRecord tmpAr = new AdjustmentRecord();
		tmpAr.setPact(pact);
		tmpAr.setIsUnsettled(true);
		final AdjustmentRecord newAr = adjustmentRecordRepository.save(tmpAr);

		// copy constituents, bind to new adjustment record and save
		List<Constituent> newCons = unsettledCons
				.stream()
				.map(c -> {
					Constituent newC = new Constituent(c);
					newC.setId(null);
					newC.setAdjustmentRecord(newAr);
					return newC;
				})
				.collect(Collectors.toList());
		newCons = constituentRepository.saveAll(newCons);

		// copy benchmarks, bind to new adjustment record and save
		List<Benchmark> newBms = benchmarkService
				.getBenchmarksByAdjustmentRecordId(unsettledArId)
				.stream()
				.map(b -> {
					Benchmark newB = new Benchmark(b);
					newB.setId(null);
					newB.setAdjustmentRecord(newAr);
					return newB;
				})
				.collect(Collectors.toList());
		newBms = benchmarkRepository.saveAll(newBms);

		// copy performance, bind to new adjustment record and save
		Performance unsettledPfm = performanceService
				.getPerformanceByAdjustmentRecordId(unsettledArId).get();
		Performance newPfm = new Performance(unsettledPfm);
		newPfm.setId(null);
		newPfm.setAdjustmentRecord(newAr);
		newPfm = performanceService.createPerformance(newPfm);

		return new PortfolioDetail(newAr, newCons, newBms, newPfm, ais);
	}

	@Transactional(rollbackFor = Exception.class)
	public void cancelSettle(Long pactId) {
		AdjustmentRecord ar = adjustmentRecordService
				.getLatestSettledAR(pactId)
				.orElseThrow(() -> new RuntimeException(
						"No latest adjustment record found for pactId: " + pactId));

		// delete the latest adjustment record
		adjustmentRecordService.deleteAR(ar.getId());

		// delete benchmarks
		benchmarkService.deleteBenchmarksByAdjustmentRecordId(ar.getId());

		// delete constituents
		constituentService.deleteConstituentsByAdjustmentRecordId(ar.getId());

		// delete performance
		performanceService.deletePerformanceByAdjustmentRecordId(ar.getId());
	}

}
