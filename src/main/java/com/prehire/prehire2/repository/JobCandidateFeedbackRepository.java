package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateFeedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCandidateFeedbackRepository extends JpaRepository<JobCandidateFeedback, Long> {
    List<JobCandidateFeedback> findByJob_Id(Long jobId);
    List<JobCandidateFeedback> findByCandidate_Id(Long candidateId);
    List<JobCandidateFeedback> findByJob_IdAndCandidate_Id(Long jobId, Long candidateId);
}
