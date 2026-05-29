package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobCandidateQuestionResponse;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobCandidateQuestionResponseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobCandidateQuestionResponseService {

    private final JobCandidateQuestionResponseRepository jobCandidateQuestionResponseRepository;

    public JobCandidateQuestionResponse getResponseById(Long id) {
        return jobCandidateQuestionResponseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question response not found: " + id));
    }

    public List<JobCandidateQuestionResponse> getResponsesByCandidateTest(Long candidateTestId) {
        return jobCandidateQuestionResponseRepository.findByCandidateTest_Id(candidateTestId);
    }

    public List<JobCandidateQuestionResponse> getResponsesByCandidate(Long candidateId) {
        return jobCandidateQuestionResponseRepository.findByCandidate_Id(candidateId);
    }

    public List<JobCandidateQuestionResponse> getResponsesByJob(Long jobId) {
        return jobCandidateQuestionResponseRepository.findByJob_Id(jobId);
    }

    public List<JobCandidateQuestionResponse> getResponsesByQuestion(Long questionId) {
        return jobCandidateQuestionResponseRepository.findByQuestion_Id(questionId);
    }
}

