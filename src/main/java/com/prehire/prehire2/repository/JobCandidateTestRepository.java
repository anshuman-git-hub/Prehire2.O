package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateTest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateTestRepository extends JpaRepository<JobCandidateTest, Long> {

    List<JobCandidateTest> findByCandidate_Id(Long candidateId);

    List<JobCandidateTest> findByJob_Id(Long jobId);

    List<JobCandidateTest> findByTest_Id(Long testId);

    List<JobCandidateTest> findByJob_IdAndCandidate_Id(Long jobId, Long candidateId);
}

