package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateScreeningSubmission;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateScreeningSubmissionRepository
        extends JpaRepository<JobCandidateScreeningSubmission, UUID> {

    List<JobCandidateScreeningSubmission> findByJob_Id(UUID jobId);

    List<JobCandidateScreeningSubmission> findByCandidate_Id(UUID candidateId);

    List<JobCandidateScreeningSubmission> findByJob_IdAndCandidate_Id(UUID jobId, UUID candidateId);

    List<JobCandidateScreeningSubmission> findByQuestion_Id(UUID questionId);
}
