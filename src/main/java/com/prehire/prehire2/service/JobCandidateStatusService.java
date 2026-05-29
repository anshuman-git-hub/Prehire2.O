package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobCandidateStatus;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobCandidateStatusRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidateStatusService {

    private final JobCandidateStatusRepository jobCandidateStatusRepository;

    public JobCandidateStatus getStatusById(Long id) {
        return jobCandidateStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job candidate status not found: " + id));
    }

    public List<JobCandidateStatus> getStatusesByJob(Long jobId) {
        return jobCandidateStatusRepository.findByJob_Id(jobId);
    }

    public List<JobCandidateStatus> getStatusesByCandidate(Long candidateId) {
        return jobCandidateStatusRepository.findByCandidate_Id(candidateId);
    }

    public List<JobCandidateStatus> getStatusesByTenant(Long tenantId) {
        return jobCandidateStatusRepository.findByTenant_Id(tenantId);
    }

    public JobCandidateStatus getStatusByJobAndCandidate(Long jobId, Long candidateId) {
        return jobCandidateStatusRepository.findByJob_IdAndCandidate_Id(jobId, candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job candidate status not found for job " + jobId + " and candidate " + candidateId
                ));
    }

    public List<JobCandidateStatus> getStatusesByCurrentStage(Long stageId) {
        return jobCandidateStatusRepository.findByCurrentStage_Id(stageId);
    }
}

