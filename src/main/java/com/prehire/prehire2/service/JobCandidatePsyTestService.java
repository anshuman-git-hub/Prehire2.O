package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobCandidatePsyTest;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobCandidatePsyTestRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidatePsyTestService {

    private final JobCandidatePsyTestRepository jobCandidatePsyTestRepository;

    public JobCandidatePsyTest getCandidatePsyTestById(Long id) {
        return jobCandidatePsyTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate psychometric test not found: " + id));
    }

    public List<JobCandidatePsyTest> getCandidatePsyTestsByTenant(Long tenantId) {
        return jobCandidatePsyTestRepository.findByTenant_Id(tenantId);
    }

    public List<JobCandidatePsyTest> getCandidatePsyTestsByJob(Long jobId) {
        return jobCandidatePsyTestRepository.findByJob_Id(jobId);
    }

    public List<JobCandidatePsyTest> getCandidatePsyTestsByCandidate(Long candidateId) {
        return jobCandidatePsyTestRepository.findByCandidate_Id(candidateId);
    }

    public JobCandidatePsyTest getCandidatePsyTestByJobAndCandidate(Long jobId, Long candidateId) {
        return jobCandidatePsyTestRepository.findByJob_IdAndCandidate_Id(jobId, candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate psychometric test not found for job " + jobId + " and candidate " + candidateId
                ));
    }
}
