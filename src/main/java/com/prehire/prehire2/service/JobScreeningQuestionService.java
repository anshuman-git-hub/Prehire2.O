package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobScreeningQuestion;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobScreeningQuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobScreeningQuestionService {

    private final JobScreeningQuestionRepository jobScreeningQuestionRepository;

    public JobScreeningQuestion getQuestionById(Long id) {
        return jobScreeningQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screening question not found: " + id));
    }

    public List<JobScreeningQuestion> getQuestionsByJob(Long jobId) {
        return jobScreeningQuestionRepository.findByJob_IdOrderBySequenceOrderAsc(jobId);
    }

    public List<JobScreeningQuestion> getQuestionsByTenant(Long tenantId) {
        return jobScreeningQuestionRepository.findByTenant_Id(tenantId);
    }

    public List<JobScreeningQuestion> getQuestionsCreatedBy(Long createdById) {
        return jobScreeningQuestionRepository.findByCreatedBy_Id(createdById);
    }
}

