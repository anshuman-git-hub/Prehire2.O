package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidatePsyTest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidatePsyTestRepository extends JpaRepository<JobCandidatePsyTest, Long> {

    List<JobCandidatePsyTest> findByTenant_Id(Long tenantId);

    List<JobCandidatePsyTest> findByJob_Id(Long jobId);

    List<JobCandidatePsyTest> findByCandidate_Id(Long candidateId);

    Optional<JobCandidatePsyTest> findByJob_IdAndCandidate_Id(Long jobId, Long candidateId);
}
