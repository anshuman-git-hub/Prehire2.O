package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateInterview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCandidateInterviewRepository extends JpaRepository<JobCandidateInterview, Long> {
    List<JobCandidateInterview> findByJob_Id(Long jobId);
    List<JobCandidateInterview> findByCandidate_Id(Long candidateId);
    List<JobCandidateInterview> findByJob_IdAndCandidate_Id(Long jobId, Long candidateId);
}
