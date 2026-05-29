package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobCandidateTest;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobCandidateTestRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidateTestService {

    private final JobCandidateTestRepository jobCandidateTestRepository;

    public JobCandidateTest getCandidateTestById(Long id) {
        return jobCandidateTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate test not found: " + id));
    }

    public List<JobCandidateTest> getCandidateTests(Long candidateId) {
        return jobCandidateTestRepository.findByCandidate_Id(candidateId);
    }

    public List<JobCandidateTest> getJobTests(Long jobId) {
        return jobCandidateTestRepository.findByJob_Id(jobId);
    }

    public List<JobCandidateTest> getAssignmentsByTest(Long testId) {
        return jobCandidateTestRepository.findByTest_Id(testId);
    }

    public List<JobCandidateTest> getCandidateTestsForJob(Long jobId, Long candidateId) {
        return jobCandidateTestRepository.findByJob_IdAndCandidate_Id(jobId, candidateId);
    }
}

