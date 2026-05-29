package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.PsyTestSubmission;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.PsyTestSubmissionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PsyTestSubmissionService {

    private final PsyTestSubmissionRepository psyTestSubmissionRepository;

    public PsyTestSubmission getSubmissionById(Long id) {
        return psyTestSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psychometric submission not found: " + id));
    }

    public List<PsyTestSubmission> getSubmissionsByPsyTest(Long psyTestId) {
        return psyTestSubmissionRepository.findByPsyTest_Id(psyTestId);
    }

    public List<PsyTestSubmission> getSubmissionsByCandidate(Long candidateId) {
        return psyTestSubmissionRepository.findByCandidate_Id(candidateId);
    }

    public List<PsyTestSubmission> getSubmissionsByJob(Long jobId) {
        return psyTestSubmissionRepository.findByJob_Id(jobId);
    }

    public List<PsyTestSubmission> getSubmissionsByQuestion(Long questionId) {
        return psyTestSubmissionRepository.findByQuestionId(questionId);
    }
}
