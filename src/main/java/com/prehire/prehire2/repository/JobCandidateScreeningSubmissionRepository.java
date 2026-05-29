package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateScreeningSubmission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateScreeningSubmissionRepository
        extends JpaRepository<JobCandidateScreeningSubmission, Long> {

    List<JobCandidateScreeningSubmission> findByJob_Id(Long jobId);

    List<JobCandidateScreeningSubmission> findByCandidate_Id(Long candidateId);

    List<JobCandidateScreeningSubmission> findByJob_IdAndCandidate_Id(Long jobId, Long candidateId);

    List<JobCandidateScreeningSubmission> findByQuestion_Id(Long questionId);
}

