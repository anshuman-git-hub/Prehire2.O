package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobCandidateTestSkillResult;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobCandidateTestSkillResultRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidateTestSkillResultService {

    private final JobCandidateTestSkillResultRepository jobCandidateTestSkillResultRepository;

    public JobCandidateTestSkillResult getResultById(Long id) {
        return jobCandidateTestSkillResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test skill result not found: " + id));
    }

    public List<JobCandidateTestSkillResult> getResultsByCandidate(Long candidateId) {
        return jobCandidateTestSkillResultRepository.findByCandidate_Id(candidateId);
    }

    public List<JobCandidateTestSkillResult> getResultsByJob(Long jobId) {
        return jobCandidateTestSkillResultRepository.findByJob_Id(jobId);
    }

    public List<JobCandidateTestSkillResult> getResultsByTest(Long testId) {
        return jobCandidateTestSkillResultRepository.findByTest_Id(testId);
    }

    public List<JobCandidateTestSkillResult> getResultsByTestSkillMapping(Long testSkillMappingId) {
        return jobCandidateTestSkillResultRepository.findByTestSkillMapping_Id(testSkillMappingId);
    }
}

