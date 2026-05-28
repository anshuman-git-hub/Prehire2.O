package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateTest;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateTestRepository extends JpaRepository<JobCandidateTest, UUID> {

    List<JobCandidateTest> findByCandidate_Id(UUID candidateId);

    List<JobCandidateTest> findByJob_Id(UUID jobId);

    List<JobCandidateTest> findByTest_Id(UUID testId);

    List<JobCandidateTest> findByJob_IdAndCandidate_Id(UUID jobId, UUID candidateId);
}
