package com.prehire.prehire2.service;

import com.prehire.prehire2.entity.JobScreeningQuestion;
import com.prehire.prehire2.exception.ResourceNotFoundException;
import com.prehire.prehire2.repository.JobScreeningQuestionRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobScreeningQuestionService {

    private final JobScreeningQuestionRepository jobScreeningQuestionRepository;

    public JobScreeningQuestion getQuestionById(UUID id) {
        return jobScreeningQuestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screening question not found: " + id));
    }

    public List<JobScreeningQuestion> getQuestionsByJob(UUID jobId) {
        return jobScreeningQuestionRepository.findByJob_IdOrderBySequenceOrderAsc(jobId);
    }

    public List<JobScreeningQuestion> getQuestionsByTenant(UUID tenantId) {
        return jobScreeningQuestionRepository.findByTenant_Id(tenantId);
    }

    public List<JobScreeningQuestion> getQuestionsCreatedBy(UUID createdById) {
        return jobScreeningQuestionRepository.findByCreatedBy_Id(createdById);
    }
}
