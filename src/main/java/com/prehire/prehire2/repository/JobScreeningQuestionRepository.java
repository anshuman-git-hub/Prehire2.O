package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobScreeningQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobScreeningQuestionRepository extends JpaRepository<JobScreeningQuestion, Long> {

    List<JobScreeningQuestion> findByJob_IdOrderBySequenceOrderAsc(Long jobId);

    List<JobScreeningQuestion> findByTenant_Id(Long tenantId);

    List<JobScreeningQuestion> findByCreatedBy_Id(Long createdById);
}

