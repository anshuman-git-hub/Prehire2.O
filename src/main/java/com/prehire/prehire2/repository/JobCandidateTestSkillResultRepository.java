package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobCandidateTestSkillResult;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCandidateTestSkillResultRepository
        extends JpaRepository<JobCandidateTestSkillResult, UUID> {

    List<JobCandidateTestSkillResult> findByCandidate_Id(UUID candidateId);

    List<JobCandidateTestSkillResult> findByJob_Id(UUID jobId);

    List<JobCandidateTestSkillResult> findByTest_Id(UUID testId);

    List<JobCandidateTestSkillResult> findByTestSkillMapping_Id(UUID testSkillMappingId);
}
