/**
 * Created by Jacob Xie on 3/10/2022.
 */

package com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.repositories;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.ubiquitousassetmanagement.portfolio.models.AdjustmentRecord;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface AdjustmentRecordRepository
		extends JpaRepository<AdjustmentRecord, Long>, JpaSpecificationExecutor<AdjustmentRecord> {

	final String queryIdsByPactId = """
			SELECT ar.id
			FROM AdjustmentRecord ar
			WHERE ar.pact.id = :pactId
			""";

	@Query(value = queryIdsByPactId)
	List<Long> findIdsByPactId(@Param("pactId") Long pactId);

	// sort by adjust_date desc and adjust_version desc
	final String queryDescSort = """
			SELECT p
			FROM AdjustmentRecord p
			WHERE p.pact.id = ?1
			ORDER BY p.adjustDate DESC, p.adjustVersion DESC
			""";

	@Query(value = queryDescSort)
	List<AdjustmentRecord> findByPactIdDescSort(Long pactId, Pageable pageable);

	final String queryUnsettledByPactId = """
			SELECT p
			FROM AdjustmentRecord p
			WHERE p.pact.id = ?1
			AND p.isUnsettled = true
			""";

	@Query(value = queryUnsettledByPactId)
	Optional<AdjustmentRecord> findUnsettledByPactId(Long pactId);

	final String queryLatestAdjustDate = """
			SELECT p1
			FROM AdjustmentRecord p1
			WHERE p1.pact.id = ?1
			AND p1.adjustDate = (
			  SELECT MAX(p2.adjustDate) FROM AdjustmentRecord p2
			  WHERE p2.pact.id = ?1
			)
			ORDER BY p1.adjustVersion DESC
			""";

	@Query(queryLatestAdjustDate)
	List<AdjustmentRecord> findByPactIdAndLatestAdjustDate(Long pactId);

	final String queryUnsettledByPactIds = """
			SELECT p
			FROM AdjustmentRecord p
			WHERE p.pact.id IN ?1
			AND p.isUnsettled = true
			""";

	@Query(value = queryUnsettledByPactIds)
	List<AdjustmentRecord> findUnsettledByPactIds(List<Long> pactIds);

	final String queryActiveLatestAdjustDateVersion = """
			SELECT p.*
			FROM portfolio_adjustment_record p
			INNER JOIN (
			  SELECT p1.portfolio_pact_id, p1.adjust_date, max(p1.adjust_version) max_version
			  FROM portfolio_adjustment_record p1
			  INNER JOIN (
			      SELECT p2.portfolio_pact_id, MAX(p2.adjust_date) AS max_date
			      FROM portfolio_adjustment_record p2
			      WHERE p2.portfolio_pact_id in :pactIds
			      GROUP BY p2.portfolio_pact_id
			  ) p3
			  ON p1.portfolio_pact_id = p3.portfolio_pact_id AND p1.adjust_date = p3.max_date
			  WHERE p1.portfolio_pact_id in :pactIds
			  GROUP BY p1.portfolio_pact_id, p1.adjust_date
			) p4
			ON p.portfolio_pact_id = p4.portfolio_pact_id
			AND p.adjust_date = p4.adjust_date
			AND p.adjust_version = p4.max_version
			WHERE p.portfolio_pact_id in :pactIds
			""";

	@Query(value = queryActiveLatestAdjustDateVersion, nativeQuery = true)
	List<AdjustmentRecord> findByPactIdsAndLatestAdjustDateVersion(@Param("pactIds") List<Long> pactIds);

	final String findAllRecordIdsByPactId = """
			SELECT ar.id FROM AdjustmentRecord ar WHERE ar.pact.id = ?1
			""";

	@Query(value = findAllRecordIdsByPactId)
	List<Long> findAllRecordIdsByPactId(Long pactId);

	final String deleteAllRecordsByPactId = """
			DELETE AdjustmentRecord ar WHERE ar.pact.id = :pactId
			""";

	@Modifying
	@Query(deleteAllRecordsByPactId)
	void deleteAllRecordsByPactId(@Param("pactId") Long pactId);
}
