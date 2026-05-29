package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobCandidateFeedback;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobCandidateFeedbackRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidateFeedbackService {

    private final JobCandidateFeedbackRepository feedbackRepository;

    public JobCandidateFeedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found: " + id));
    }

    public List<JobCandidateFeedback> getFeedbackByJob(Long jobId) {
        return feedbackRepository.findByJob_Id(jobId);
    }

    public List<JobCandidateFeedback> getFeedbackByCandidate(Long candidateId) {
        return feedbackRepository.findByCandidate_Id(candidateId);
    }

    public List<JobCandidateFeedback> getFeedbackByJobAndCandidate(Long jobId, Long candidateId) {
        return feedbackRepository.findByJob_IdAndCandidate_Id(jobId, candidateId);
    }

    @Transactional
    public JobCandidateFeedback saveFeedback(JobCandidateFeedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Transactional
    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
}
