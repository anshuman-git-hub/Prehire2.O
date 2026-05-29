package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateTestSkillResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateTestSkillResultRepository
        extends JpaRepository<JobCandidateTestSkillResult, Long> {

    List<JobCandidateTestSkillResult> findByCandidate_Id(Long candidateId);

    List<JobCandidateTestSkillResult> findByJob_Id(Long jobId);

    List<JobCandidateTestSkillResult> findByTest_Id(Long testId);

    List<JobCandidateTestSkillResult> findByTestSkillMapping_Id(Long testSkillMappingId);
}

