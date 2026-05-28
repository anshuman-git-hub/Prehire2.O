package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateStatusRepository extends JpaRepository<JobCandidateStatus, UUID> {

    List<JobCandidateStatus> findByJob_Id(UUID jobId);

    List<JobCandidateStatus> findByCandidate_Id(UUID candidateId);

    List<JobCandidateStatus> findByTenant_Id(UUID tenantId);

    Optional<JobCandidateStatus> findByJob_IdAndCandidate_Id(UUID jobId, UUID candidateId);

    List<JobCandidateStatus> findByCurrentStage_Id(Long stageId);
}
