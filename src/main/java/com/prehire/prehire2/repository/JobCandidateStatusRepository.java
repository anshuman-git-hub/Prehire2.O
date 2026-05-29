package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateStatusRepository extends JpaRepository<JobCandidateStatus, Long> {

    List<JobCandidateStatus> findByJob_Id(Long jobId);

    List<JobCandidateStatus> findByCandidate_Id(Long candidateId);

    List<JobCandidateStatus> findByTenant_Id(Long tenantId);

    Optional<JobCandidateStatus> findByJob_IdAndCandidate_Id(Long jobId, Long candidateId);

    List<JobCandidateStatus> findByCurrentStage_Id(Long stageId);
}

