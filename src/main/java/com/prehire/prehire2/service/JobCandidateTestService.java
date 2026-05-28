package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobCandidateTest;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobCandidateTestRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidateTestService {

    private final JobCandidateTestRepository jobCandidateTestRepository;

    public JobCandidateTest getCandidateTestById(UUID id) {
        return jobCandidateTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate test not found: " + id));
    }

    public List<JobCandidateTest> getCandidateTests(UUID candidateId) {
        return jobCandidateTestRepository.findByCandidate_Id(candidateId);
    }

    public List<JobCandidateTest> getJobTests(UUID jobId) {
        return jobCandidateTestRepository.findByJob_Id(jobId);
    }

    public List<JobCandidateTest> getAssignmentsByTest(UUID testId) {
        return jobCandidateTestRepository.findByTest_Id(testId);
    }

    public List<JobCandidateTest> getCandidateTestsForJob(UUID jobId, UUID candidateId) {
        return jobCandidateTestRepository.findByJob_IdAndCandidate_Id(jobId, candidateId);
    }
}
