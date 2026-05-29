package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobCandidateInterview;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobCandidateInterviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidateInterviewService {

    private final JobCandidateInterviewRepository interviewRepository;

    public JobCandidateInterview getInterviewById(Long id) {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found: " + id));
    }

    public List<JobCandidateInterview> getInterviewsByJob(Long jobId) {
        return interviewRepository.findByJob_Id(jobId);
    }

    public List<JobCandidateInterview> getInterviewsByCandidate(Long candidateId) {
        return interviewRepository.findByCandidate_Id(candidateId);
    }

    public List<JobCandidateInterview> getInterviewsByJobAndCandidate(Long jobId, Long candidateId) {
        return interviewRepository.findByJob_IdAndCandidate_Id(jobId, candidateId);
    }

    @Transactional
    public JobCandidateInterview saveInterview(JobCandidateInterview interview) {
        return interviewRepository.save(interview);
    }

    @Transactional
    public void deleteInterview(Long id) {
        interviewRepository.deleteById(id);
    }
}
