package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobCandidateQuestionResponse;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobCandidateQuestionResponseRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidateQuestionResponseService {

    private final JobCandidateQuestionResponseRepository jobCandidateQuestionResponseRepository;

    public JobCandidateQuestionResponse getResponseById(UUID id) {
        return jobCandidateQuestionResponseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question response not found: " + id));
    }

    public List<JobCandidateQuestionResponse> getResponsesByCandidateTest(UUID candidateTestId) {
        return jobCandidateQuestionResponseRepository.findByCandidateTest_Id(candidateTestId);
    }

    public List<JobCandidateQuestionResponse> getResponsesByCandidate(UUID candidateId) {
        return jobCandidateQuestionResponseRepository.findByCandidate_Id(candidateId);
    }

    public List<JobCandidateQuestionResponse> getResponsesByJob(UUID jobId) {
        return jobCandidateQuestionResponseRepository.findByJob_Id(jobId);
    }

    public List<JobCandidateQuestionResponse> getResponsesByQuestion(UUID questionId) {
        return jobCandidateQuestionResponseRepository.findByQuestion_Id(questionId);
    }
}
