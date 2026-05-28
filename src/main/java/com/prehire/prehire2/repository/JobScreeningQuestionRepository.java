package com.prehire.prehire2.repository;

import com.prehire.prehire2.entity.JobScreeningQuestion;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobScreeningQuestionRepository extends JpaRepository<JobScreeningQuestion, UUID> {

    List<JobScreeningQuestion> findByJob_IdOrderBySequenceOrderAsc(UUID jobId);

    List<JobScreeningQuestion> findByTenant_Id(UUID tenantId);

    List<JobScreeningQuestion> findByCreatedBy_Id(UUID createdById);
}
